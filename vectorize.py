#file_list = input() # at top so some library magic will work for sure

# -*- coding: utf-8 -*-
"""
Created on Mon Dec  5 17:34:55 2016
@author: Kyle Mann
"""
# use PyQt for ui?
#use Nuitka or py2exe to turn into .exe for windows os - stand alone have to make input a list for multiple files

from PIL import Image
from progressbar import ProgressBar
import os
#drag and drop in windows?-------------------------------


#transform into bitmap or vector------------------------
    #ask for output type bmp,------------------- 
def find_file_type(dicto):
    filetype = input('What file type do you want?  BMP, JPG')
    filetype = filetype.upper()
    assert(filetype in dicto)
    #print(dicto[filetype])    
    return dicto[filetype] # this has to put out the file extension for convert to use
    
#save new file in same directory-------------------------------

def convert(old_file, filetype, dicto):
    im = Image.open(old_file) 
    currentpath = os.path.realpath(old_file) # how to get directory name of old file
    foldertree = currentpath.split('/') #should remove /'s 
    redirec = ''
    for folder in foldertree:
        redirec = redirec+folder+'/'
    redirec = redirec[:len(redirec)-5] #gets rid of extra '/'
    im.save(redirec, filetype)    
    return (redirec+dicto[filetype])
'''    
#preview new file----------------------------------------
def preview(image):
    if os.path.exists(r"C:\Program Files(x86)\IrfanView"):
        print('not yet finished')
    else:
        os.system(r"C:\Program Files(x86)\Windows Photo Viewer")
#ask if file should be exported to reitna-----------------------
'''

def main(file_list):
    supported_dict = {'BMP':'.bmp', 'JPG':'.jpg', 'PNG':'.png'}   
    want_preview = False    
    file_type = find_file_type(supported_dict)    
    pbar = ProgressBar(maxval = len(file_type)).start()   
    i = 0    
    for file in file_list:
        new_file = convert(file, file_type, supported_dict)
        '''if want_preview == True:
            preview(new_file)'''
        pbar.update(i+1)
    pbar.finish()

if __name__ == '__main__':
    file_list = [r"C:\Users\Kyle\Tulips.jpg"] #this is a temporary list, an exe should collect from the top line
    main(file_list)