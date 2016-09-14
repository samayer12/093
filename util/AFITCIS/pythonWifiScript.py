import os
import time


while True:
   if os.system("ping -c 1 www.google.com") == 0:
      print "connection good"
   else:
      print "no connection, accepting wifi connection"
