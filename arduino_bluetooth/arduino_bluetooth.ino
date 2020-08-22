#include "SPI.h"
#include "Adafruit_GFX.h"
#include "Adafruit_ILI9341.h"




Adafruit_ILI9341 tft = Adafruit_ILI9341(10, 9, 11, 13, 8, 12);



char i_byte = 0;
 
  
                
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
  if(Serial.available() > 0)  
  {
    i_byte = Serial.read();         
    if(i_byte == '1')             
      tft.fillScreen(ILI9341_RED);
    else if(i_byte == '0')       
      tft.fillScreen(ILI9341_BLUE);
  }                            
}
