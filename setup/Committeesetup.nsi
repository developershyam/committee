!include nsDialogs.nsh
!include LogicLib.nsh
!include "MUI.nsh"
;NSIS Modern User Interface version 1.70
;Committee Installer Script
;Written by shyam sunder pareek

;--------------------------------
;Include Modern UI


;--------------------------------
;General

  ;Name and file
  Name "Committee-2.1"
  OutFile "Committee-2.1-setup.exe"

  ;Default installation folder
  InstallDir "$PROGRAMFILES\xieon\Committee-2.1"

;installation instraction checking
;----------------------------------------
Function .onInstSuccess ;After installation success the installer writes some values to registry

WriteRegStr HKLM "Software\xieon\Committee-2.1" "installed" "yes"

FunctionEnd

;and in the extension installer:

Function .onInit

ReadRegStr $0 HKLM "Software\xieon\Committee-2.1" "installed"
FunctionEnd
  
;Get installation folder from registry if available
  InstallDirRegKey HKCU "Software\xieon\Committee-2.1" "installed"

;--------------------------------
;Interface Settings

        !define MUI_ABORTWARNING
	!define MUI_HEADERIMAGE "gold.bmp"
	!define MUI_HEADERIMAGE_BITMAP_NOSTRETCH
	!define MUI_HEADERIMAGE_BITMAP "gold.bmp"
	!define MUI_ICON "gold.ico"
	!define MUI_UNICON "setup.ico"

;--------------------------------
;Pages

  !insertmacro MUI_PAGE_LICENSE "License.txt"
  
  !insertmacro MUI_PAGE_COMPONENTS 
  !insertmacro MUI_PAGE_DIRECTORY
  !insertmacro MUI_PAGE_INSTFILES
  
  !insertmacro MUI_UNPAGE_CONFIRM
  !insertmacro MUI_UNPAGE_INSTFILES
  
;--------------------------------
;Languages
 
  !insertmacro MUI_LANGUAGE "English"

;--------------------------------
;Installer Sections

Section "Committee-2.1 (required)" SecDummy

  SectionIn RO

  ;Files to be installed
  SetOutPath "$INSTDIR"
  
   File "Committee-2.1.jar"
   File "setup.ico"
   File "gold.ico"
   File "gold.png"
   File "gold.bmp"
   File "License.txt"
   
   ; Write the installation path into the registry
   WriteRegStr HKLM SOFTWARE\xieon\Committee-2.1 "Install_Dir" "$INSTDIR"
  
  ; Write the uninstall keys for Windows
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\xieon\shyam" "DisplayName" "Committee-2.1"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\xieon\shyam" "UninstallString" '"$INSTDIR\uninstall.exe"'
  WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\xieon\shyam" "NoModify" 1
  WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\xieon\shyam" "NoRepair" 1
  WriteUninstaller "uninstall.exe"
  
  SetOutPath "$INSTDIR\lib"
  File "lib\mysql-connector-java-5.1.6-bin.jar"
  SetOutPath "$INSTDIR\src"
   File "src\config.properties"

SectionEnd

; Optional section (can be disabled by the user)
Section "Start Menu Shortcuts"
  CreateDirectory "$SMPROGRAMS\Committee-2.1"
  CreateShortCut "$SMPROGRAMS\Committee-2.1\Uninstall.lnk" "$INSTDIR\uninstall.exe" "" "$INSTDIR\uninstall.exe"
  CreateShortCut "$SMPROGRAMS\Committee-2.1\Committee-2.1.lnk" "$INSTDIR\Committee-2.1.jar" "" "$INSTDIR\gold.ico"

  CreateShortCut "$DESKTOP\Committee-2.1.lnk" "$INSTDIR\Committee-2.1.jar" "" "$INSTDIR\gold.ico"
SectionEnd





;--------------------------------
;Uninstaller Section

Section "Uninstall"

  ; Remove registry keys
  DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\xieon\Committee-2.1"
  DeleteRegKey HKLM SOFTWARE\xieon
  DeleteRegKey /ifempty HKCU "Software\xieon\Committee-2.1"

  ; Remove shortcuts
  RMDir /r "$SMPROGRAMS\"
  Delete "$DESKTOP\Committee-2.1.lnk"

  ; Remove directories used
  RMDir /r "$INSTDIR"

SectionEnd