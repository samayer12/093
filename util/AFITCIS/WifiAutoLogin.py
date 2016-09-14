from splinter.browser import Browser
from time import sleep

URL = 'https://1.1.1.2/login.html'
NAME = 'noname'
PASSWORD = 'none'

def main():
   br = Browser('chrome')
   br.visit( URL )
   sleep( 3 )
   if br.is_text_present( 'United States Air Force provided Commercial Internet Service', wait_time=7 )
      br.find_by_css( 'Submit' ).first.click()
      
      
if __name__ = "__main__":
   main()
