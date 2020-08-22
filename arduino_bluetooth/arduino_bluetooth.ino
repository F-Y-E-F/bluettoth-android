#include "SPI.h"
#include "Adafruit_GFX.h"
#include "Adafruit_ILI9341.h"




Adafruit_ILI9341 tft = Adafruit_ILI9341(10, 9, 11, 13, 8, 12);



char i_byte = 0;
 

                
void setup() 
{
  Serial.begin(9600);        
  tft.begin(); 
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
