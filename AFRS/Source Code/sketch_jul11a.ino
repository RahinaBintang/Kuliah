#include <WiFi.h>
#include <WiFiClient.h>
#include <WebServer.h>
#include <ESPmDNS.h>
#include <ESP32Servo.h>

char ssid[] = "Alfara 76";   // isikan nama wifi
char pass[] = "alfara78";   // isikan password wifi

#define LDR_PIN 32        // pin LDR dihubungkan ke pin 32
#define SERVO_PIN 4       // pin Servo dihubungkan ke pin 4
#define LED_PIN 15        // pin LED dihubungkan ke pin 15

int lightValue = 0;       // variabel untuk menyimpan nilai sensor LDR

Servo myServo;

WebServer server(80);

void handleRoot()
{
  String html = "<html>";
  html += "<head>";
  html += "<title>AFRS</title>";
  html += "<style>";
  html += "body { background-color: #cce6ff; font-family: Arial, sans-serif; }";
  html += ".container { max-width: 400px; margin: 0 auto; padding: 20px; background-color: #fff; border-radius: 5px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); }";
  html += "h1 { color: #333; text-align: center; }";
  html += "table { width: 100%; border-collapse: collapse; margin-bottom: 10px; }";
  html += "table th, table td { padding: 8px; text-align: left; border-bottom: 1px solid #ddd; }";
  html += "table th { background-color: #f2f2f2; }";
  html += "p { color: #666; }";
  html += "a.button { display: inline-block; background-color: #0066cc; color: #fff; padding: 10px 20px; border-radius: 5px; text-decoration: none; }";
  html += "a.button:hover { background-color: #0052a3; }";
  html += "</style>";
  html += "</head>";
  html += "<body>";
  html += "<div class='container'>";
  html += "<h1>Automatic Fish Rearing System</h1>";
  html += "<table>";
  html += "<tr><th>Light Value</th><td id='light-value'>" + String(lightValue) + "</td></tr>";
  html += "<tr><th>Kondisi Cahaya</th><td id='light-condition'>" + getLightCondition() + "</td></tr>";
  html += "</table>";
  html += "<p><a class='button' href='/servo'>Beri Pakan Ikan</a></p>";
  html += "</div>";
  html += "<script>";
  html += "setInterval(function() {";
  html += "  var xhttp = new XMLHttpRequest();";
  html += "  xhttp.onreadystatechange = function() {";
  html += "    if (this.readyState == 4 && this.status == 200) {";
  html += "      var data = JSON.parse(this.responseText);";
  html += "      document.getElementById('light-value').innerHTML = data.lightValue;";
  html += "      document.getElementById('light-condition').innerHTML = data.lightCondition;";
  html += "    }";
  html += "  };";
  html += "  xhttp.open('GET', '/data', true);";
  html += "  xhttp.send();";
  html += "}, 1000);";
  html += "</script>";
  html += "</body>";
  html += "</html>";

  server.send(200, "text/html", html);
}

void handleLightValue()
{
  String lightData = String(lightValue);
  String lightCondition = getLightCondition();
  server.sendHeader("Light-Condition", lightCondition);
  server.send(200, "text/plain", lightData);
}

void handleServo()
{
  moveServo();
  server.send(200, "text/plain", "Servo moved");
}

String getLightCondition()
{
  if (lightValue < 500)
  {
    return "Gelap";
  }
  else
  {
    return "Terang";
  }
}

void handleData()
{
  String lightData = String(lightValue);
  String lightCondition = getLightCondition();
  
  String jsonData = "{\"lightValue\":\"" + lightData + "\", \"lightCondition\":\"" + lightCondition + "\"}";

  server.send(200, "application/json", jsonData);
}

void setup()
{
  Serial.begin(115200);

  WiFi.begin(ssid, pass);

  while (WiFi.status() != WL_CONNECTED)
  {
    delay(1000);
    Serial.println("Connecting to WiFi...");
  }

  Serial.println("Connected to WiFi");
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());

  if (!MDNS.begin("esp32"))
  {
    Serial.println("Error setting up MDNS responder!");
  }

  myServo.attach(SERVO_PIN);
  pinMode(LDR_PIN, INPUT);
  pinMode(LED_PIN, OUTPUT);

  server.on("/", handleRoot);
  server.on("/servo", handleServo);
  server.on("/light-value", handleLightValue);
  server.on("/data", handleData);

  server.begin();

  digitalWrite(LED_PIN, HIGH);
}

void loop()
{
  server.handleClient();
  checkLight();
  Serial.println("");
  delay(1000);
}

void moveServo()
{
  for (int pos = 0; pos <= 180; pos++)
  {
    myServo.write(pos);
    delay(10);
  }

  delay(500);

  for (int pos = 180; pos >= 0; pos--)
  {
    myServo.write(pos);
    delay(10);
  }
}

void checkLight()
{
  lightValue = analogRead(LDR_PIN);
  Serial.print("Intensitas Cahaya: ");
  Serial.println(lightValue);

  if (lightValue < 500)
  {
    Serial.print("\nKondisi Cahaya: Gelap\n");
    digitalWrite(LED_PIN, HIGH);
  }
  else
  {
    Serial.print("Kondisi Cahaya: Terang\n");
    digitalWrite(LED_PIN, LOW);
  }
}