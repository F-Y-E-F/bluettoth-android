#include "SPI.h"
#include "Adafruit_GFX.h"
#include "Adafruit_ILI9341.h"




Adafruit_ILI9341 tft = Adafruit_ILI9341(10, 9, 11, 13, 8, 12);



String message = "";
  
                
void setup() 
{
  Serial.begin(9600);        
  tft.begin(); 
  tft.fillScreen(ILI9341_BLACK);
  tft.drawLine(85, 45, 85 , 275, ILI9341_BLUE);
  tft.drawLine(155, 45, 155 , 275, ILI9341_BLUE);

  tft.drawLine(20, 200, 220 , 200, ILI9341_BLUE);
  tft.drawLine(20, 120, 220, 120, ILI9341_BLUE);
 
}

void loop()
{


  
  while (Serial.available() > 0) {  
     message = message + char(Serial.read());
  }

  char symbol = message[0];
  int destinationsBorder = message.indexOf(',');
  String firstDestination = message.substring(message.indexOf('(') + 1 , destinationsBorder);
  String secondDestination = message.substring( destinationsBorder + 1, message.indexOf(')'));

  Serial.println(symbol);
  Serial.println(firstDestination);
  Serial.println(secondDestination);

  tft.setCursor(0,0);
  tft.setTextColor(ILI9341_YELLOW);  tft.setTextSize(7);

  tft.setCursor(firstDestination.toInt(),secondDestination.toInt());
  tft.print(String(symbol));

  message = "";

  delay(100);
 }
