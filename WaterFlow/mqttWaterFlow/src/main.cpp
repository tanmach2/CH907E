#include <Arduino.h>
#include <PubSubClient.h>
#include <WiFi.h>
#include <ArduinoJson.h>
#include <Wire.h>
#include <LiquidCrystal_I2C.h>


LiquidCrystal_I2C lcd(0x27,16,2); 

// #define LED_BUILTIN 2
#define SENSOR 27

char *const ssid = "phamtan";
char *const passwd = "tanmach2";
char *const mqtt_server = "test.mosquitto.org";
String clientId = "ESP32-" + String(random(100), HEX);

WiFiClient esp32Client;
PubSubClient mqttClient(esp32Client);

long currentMillis = 0;
long timeDayCounter = 0;
long timeInDayCounter = 0;
long timeMonthCounter = 0;
long previousMillis = 0;
int interval = 1000;
boolean ledState = LOW;
float calibrationFactor = 4.5;
volatile byte pulseCount;
byte pulse1Sec = 0;
float flowRate;
unsigned int flowMilliLitres;
float dataDay;
float dataMonth;
float dataInDay;
float totaldataInDay = 0;
float totaldataDay = 0;
float totalMilliLitres = 0;


// reconnect wifi khi ket noi bi ngat
void reconnecMQTT() {
  Serial.print("reconnect MQTT");
  while(!mqttClient.connect(clientId.c_str())) {
    Serial.println("Try connect to MQTT...");
    if(mqttClient.connect(clientId.c_str())){
      Serial.print("connected to mqtt success");
    }
  }
}


void getDataInfo(){
  
  currentMillis = millis();
  if (currentMillis - previousMillis > interval) 
  {  
    pulse1Sec = pulseCount;
    pulseCount = 0;

    flowRate = ((1000.0 / (millis() - previousMillis)) * pulse1Sec) / calibrationFactor;
    previousMillis = millis();
    flowMilliLitres = (flowRate / 60) * 1000;

    dataInDay += flowMilliLitres;
    
    // Print the flow rate for this second in litres / minute
    Serial.print("Flow rate: ");
    Serial.print(flowRate);  // Print the integer part of the variable
    Serial.print("L/min");
    Serial.println("\t");       // Print tab space
    // Print the cumulative total of litres flowed since starting

    lcd.setCursor(0,0);
    lcd.print("water flow rate");
    lcd.setCursor(3,1);
    lcd.print(flowRate);
    lcd.setCursor(9,1);
    lcd.print("L/min");

    
  }

  if (currentMillis - timeInDayCounter == 30000)
  {
    Serial.print("Output dataInDay: ");
    Serial.print(dataInDay);
    Serial.print("mL / ");
    Serial.print(dataInDay / 1000);
    Serial.println("L");
    timeInDayCounter = currentMillis;
    totaldataInDay += dataInDay;
    // pulishData();
    StaticJsonBuffer<200> jsonBuffer;
    JsonObject& jsonWaterFlow = jsonBuffer.createObject();
    jsonWaterFlow["data_in_day"] = dataInDay;
    jsonWaterFlow["data_day"] = dataDay;
    jsonWaterFlow["data_month"] = dataMonth;
    jsonWaterFlow["device_time"] = millis();
    jsonWaterFlow["serial"] = "123";

    char bufferWaterFlow[200];
    jsonWaterFlow.printTo(bufferWaterFlow, sizeof(bufferWaterFlow));
    mqttClient.publish("waterFlow", bufferWaterFlow);
  }

  if (currentMillis - timeDayCounter == 70000)
  {
    dataDay = totaldataInDay;
    Serial.print("Output dataDay: ");
    Serial.print(dataDay);
    Serial.print("mL / ");
    Serial.print(dataDay / 1000);
    Serial.println("L");
    timeDayCounter = currentMillis;
    totaldataDay += dataDay;
  }

  if (currentMillis - timeMonthCounter== 140000)
  {
    dataMonth = totaldataDay;
    Serial.print("Output DataMonth: ");
    Serial.print(dataMonth);
    Serial.print("mL / ");
    Serial.print(dataMonth / 1000);
    Serial.println("L");
    timeMonthCounter = currentMillis;
  }

  
}




void IRAM_ATTR pulseCounter()
{
  pulseCount++;
}



void setup()
{
  Serial.begin(115200);
  Serial.print("connecting to wifi...");
  Serial.print(ssid);
  WiFi.begin(ssid,passwd);
  while(WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.print(WiFi.localIP());
  mqttClient.setServer(mqtt_server, 1883);

  // pinMode(LED_BUILTIN, OUTPUT);
  pinMode(SENSOR, INPUT_PULLUP);
  lcd.begin(16,2);
  lcd.init();
  lcd.backlight();
  lcd.setCursor(0,0);
  lcd.print("connecting wifi..");
  lcd.setCursor(3,1);
  lcd.print(ssid);
  delay(2000);

  pulseCount = 0;
  flowRate = 0.0;
  flowMilliLitres = 0;
  dataDay= 0;
  dataInDay = 0;
  dataMonth = 0;
  previousMillis = 0;
  totalMilliLitres = 0;
  lcd.clear();

  digitalWrite(SENSOR, HIGH);
  attachInterrupt(digitalPinToInterrupt(SENSOR), pulseCounter, FALLING);

}


void loop()
{
  //kiem tra connect wifi 
  if (!mqttClient.connect(clientId.c_str())){
    reconnecMQTT();
  }

  mqttClient.loop();

  getDataInfo();
  
}