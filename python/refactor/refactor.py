#!/usr/bin/env python
# Requires Python 3.0 or greater

# Test suite. Assumes subfolder 'test' of current path contains test files.
# python regEx.py "([a-z]*)_([a-z]*)_([a-z]*)" "\2_\3_\1" test\*.txt
# python regEx.py "([a-z]*)_([a-z]*)_([a-z]*)_([a-z]*)_([a-z]*)_([a-z]*)_([a-z]*)" "\5_\7_\2_\6_\3_\4_\1" test\*
# python regEx.py foo bar test\foo1 test\foo2
# python regEx.py foo bar test\*
# python regEx.py "---*" "-" test\*-*

# python refactor.py "actorLst \= new std\:\:vector\< WO\* \>\;" "this->actorLst = new WorldList();"
# REPLACES "actorLst = new std::vector< WO* >;" with
#         "this->actorLst = new WorldList();"

import sys
import os
import os.path
import glob
import re
import string

global globalViewMode
globalMatchedFilesCount = 0
globalViewMode = 1  # 1 means only view what would be modifed, 0 means perform the regex modification

def refactorTextWithinFile(oldFile, Find, Replace):
    #open file
    #read file into string
    #apply regex substitution to input string
    #if output string has changed, the regex found a match and was applied so write it to file

    import fileinput

    for line in fileinput.input(oldFile, inplace=True, backup='.bak'):
        line = re.sub(sys.argv[2], sys.argv[3], line.rstrip())
        print(line)

def showTargetedFiles(flist):
    # find all files we want to search. In this example all files in './test/*.txt'
    print("Files affected:")
    for f in flist:
        print(f)

# Display Help Text
if len(sys.argv) != 4:
    print("")
    print("Usage (from usr/modules/): refactor (v|r) RegExToFind RegExToReplace")
    print("   v: means view-only - no files will be modified. Only those")
    print("      files that would be modified are listed.")
    print("   r: replace - executes the regex on all matched files.")
    print("")
    print("Examples:")
    print("python refactor.py \"([a-z]*)_([a-z]*)_([a-z]*)\" \"\\2_\\3_\\1\"")
    print(
        "python refactor.py \"([a-z]*)_([a-z]*)_([a-z]*)_([a-z]*)_([a-z]*)_([a-z]*)_([a-z]*)\" \"\\5_\\7_\\2_\\6_\\3_\\4_\\1\"")
    print("python refactor.py foo bar")
    print("python refactor.py \"---*\" \"-\"")
    #       python renameFiles.py v "GLViewNyklDTED\.cpp" "FunDTED.cpp" (No need to escape quotes in doc)
    print("python renameFiles.py v \"GLViewNyklDTED\.cpp\" \"FunDTED.cpp\"")
    # Replace SteaMiE* with Aftr*
    # python refactor.py v "(?i)steamie([A-Za-z0-9]*)" "Aftr\1"
    quit()

# fileExt = sys.argv[1]

#Read in regular expressions
print("***REGEX UTILITY***")
print("Detected the following Regexes:")
#Compile regex to ensure validity
regexFind = re.compile(sys.argv[2])
regexRep = re.compile(sys.argv[2])
print(sys.argv[2])
print(sys.argv[3])

fileList = []
rootdir = ".\\test"
for root, subFolders, files in os.walk(rootdir):
    for file in files:
        fileList.append(os.path.join(root, file))

#and output if we are in replace or view mode
if sys.argv[1] == 'r' or sys.argv[1] == 'R':
    #we are going to actually REPLACE!!!
    print("Current mode: REPLACE")
    # for each file found, call refactorTextWithinFile which applies regex to it
    showTargetedFiles(fileList)
    print("Refactoring files...")
    for file in fileList:
        refactorTextWithinFile(file, regexFind, regexRep)
else:
    #we are just going to view what would be replaced if 'R' was passed in.
    print("Current mode: VIEW\n")
    showTargetedFiles(fileList)