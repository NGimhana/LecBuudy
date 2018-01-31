import cv2
import win32gui
import time
import gitUpload

def windowEnumerationHandler(hwnd, top_windows):    
    top_windows.append((hwnd, win32gui.GetWindowText(hwnd)))

def windowOpenPP():
    if __name__ == "__main__":
        results = []
        top_windows = []
        win32gui.EnumWindows(windowEnumerationHandler, top_windows)
        for i in top_windows:
            if "powerpoint" in i[1].lower():
                #print (top_windows)
                #print (i)
                win32gui.ShowWindow(i[0],5)
                win32gui.SetForegroundWindow(i[0])
                break

def windowOpenCamera():
    if __name__ == "__main__":
        results = []
        top_windows = []
        win32gui.EnumWindows(windowEnumerationHandler, top_windows)
        for i in top_windows:
            if "camera" in i[1].lower():
                #print (i)
                win32gui.ShowWindow(i[0],5)
                win32gui.SetForegroundWindow(i[0])
                break

def diffImg(t0, t1, t2):
  d1 = cv2.absdiff(t2, t1)
  d2 = cv2.absdiff(t1, t0)
  return cv2.bitwise_and(d1, d2)

def showCamera(cam):
    ret , frame = cam.read()
    cv2.imshow('Camera' ,frame)
    windowOpenCamera()

# cam = cv2 Video Capture Object
def switch(retVal ,countVal):
    
    cam = cv2.VideoCapture(1)
    cv2.namedWindow("Camera")
    w=win32gui

    # Read three images first:
    t_minus = cv2.cvtColor(cam.read()[1], cv2.COLOR_RGB2GRAY)
    t = cv2.cvtColor(cam.read()[1], cv2.COLOR_RGB2GRAY)
    t_plus = cv2.cvtColor(cam.read()[1], cv2.COLOR_RGB2GRAY)
    ret,thresh = cv2.threshold(diffImg(t_minus, t, t_plus),0,255,cv2.THRESH_OTSU)
    count = 0
    co=0
    while True:
        t_minus = t
        t = t_plus
        t_plus = cv2.cvtColor(cam.read()[1], cv2.COLOR_RGB2GRAY)
        ret,thresh = cv2.threshold(diffImg(t_minus, t, t_plus),0,255,cv2.THRESH_OTSU)
        if(count < countVal and count != 0):
            state = "writing"
        elif(ret>retVal):
            count = 1
            state = "writing"
            showCamera(cam)
        else:
            count = 1
            state = "not writing"
            windowOpenPP()

        if(state == "writing"):
            while True:
                t_minus = t
                t = t_plus
                t_plus = cv2.cvtColor(cam.read()[1], cv2.COLOR_RGB2GRAY)
                ret,thresh = cv2.threshold(diffImg(t_minus, t, t_plus),0,255,cv2.THRESH_OTSU)
                if(ret <= retVal):
                    t_minus = cv2.cvtColor(cam.read()[1], cv2.COLOR_RGB2GRAY)
                    t = cv2.cvtColor(cam.read()[1], cv2.COLOR_RGB2GRAY)
                    t_plus = cv2.cvtColor(cam.read()[1], cv2.COLOR_RGB2GRAY)
                    ret,thresh = cv2.threshold(diffImg(t_minus, t, t_plus),0,255,cv2.THRESH_OTSU)
                    if(ret > retVal):
                        count = count - 1
                        showCamera(cam)
                        continue
                    else:
                        showCamera(cam)
                        count = count + 1
                        #Taking an Image to send
                        if (count == countVal):
                            co+=1
                            x, writtenIMG = cam.read()
                            cv2.imwrite("LecBuddy_v0.1/Image"+str(co)+".jpg",writtenIMG)
                            gitUpload.uploadImg("Image"+str(co)+".jpg")
                        break
            key = cv2.waitKey(10)
            if key == 27:
                cv2.destroyWindow(winName)
                break
        else:
            while True:
                t_minus = t
                t = t_plus
                t_plus = cv2.cvtColor(cam.read()[1], cv2.COLOR_RGB2GRAY)
                ret,thresh = cv2.threshold(diffImg(t_minus, t, t_plus),0,255,cv2.THRESH_OTSU)
                if(ret > retVal):
                    count = 1
                    showCamera(cam)
                    key = cv2.waitKey(10)
                    if key == 27:
                        cv2.destroyWindow(winName)
                        break
                    break
                else:
                    continue
                key = cv2.waitKey(10)
                if key == 27:
                    cv2.destroyWindow(winName)
                    break

switch(18 , 25)
print ("Goodbye")
