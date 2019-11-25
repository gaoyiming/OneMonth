# OneMonth
first commit
# _*_ coding:UTF-8 _*_
from pykeyboard import *
from pymouse import *
import webbrowser
import time
import itchat
import numpy as np
import pyscreenshot as ImageGrab


k = PyKeyboard()
m = PyMouse()


def main():
    itchat.auto_login(hotReload=True)
    itchat.run(True)


@itchat.msg_register(itchat.content.TEXT)
def _(msg):

    if (str(msg.ToUserName) == 'filehelper') & (str(msg.Content) == 'dosomething'):
        webbrowser.open("http://crm.jiandan100.cn/")
        time.sleep(5)
        m.click(990, 650)
        time.sleep(5)
        m.click(647, 230)
        pass
    elif(str(msg.ToUserName) == 'filehelper') & (str(msg.Content) == 'screenshot'):
        filename = 'temp.png'
        im = ImageGrab.grab()
        im.save(filename)
        im.close()
        itchat.send_image(filename, toUserName='filehelper')

    elif(str(msg.ToUserName) == 'filehelper') & (str(msg.Content) == 'clossbrowser'):
          pass
         

    elif(str(msg.ToUserName) == 'filehelper') & (str(msg.Content).find('click')!=-1):
        location= str(msg.Content).split(',')
        m.click(int(location[1]),int(location[2]))
         
    elif(str(msg.ToUserName) == 'filehelper') & (str(msg.Content).find('move')!=-1):

        location= str(msg.Content).split(',')
        m.move(int(location[1]),int(location[2]))
        pass
         
    else:
        pass


if __name__ == "__main__":
    main()
