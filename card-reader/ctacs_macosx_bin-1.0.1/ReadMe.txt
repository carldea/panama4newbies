ACS CT-API Mac OS X Library
Advanced Card Systems Ltd.



Introduction
------------

This library provides CT-API functions for communicating with ACS smart card
readers on Mac OS X.



Release Notes
-------------

Version:      1.0.1
Release Date: 23/1/2017

System Requirements

- Mac OS X 10.5 or above

Supported Readers

- ACS smart card readers

Supported CT-BCS Commands

- RESET
- RESET CT
- REQUEST ICC
- GET STATUS
- DEACTIVATE ICC
- EJECT ICC



Installation
------------

1. In Terminal application, enter the following command to extract files.

# sudo tar -jxvf ctacs_macosx_bin-x.y.z.tar.bz2
# cd ctacs_macosx_bin-x.y.z

2. Enter the following command to install the library files.

# sudo ./install.sh

3. Enter the following command to uninstall the library files.

# sudo ./uninstall.sh

4. Create a INI file "ctacs.ini" in the working directory with your application.

5. Edit the INI file. To define a card terminal, you need to add a entry with
   PC/SC reader name.

; Sample ctacs.ini (Linux/Mac OS X)

[CardTerminal]
CTN1=ACR38U-CCID
CTN2=ACR128U

[ACR38U-CCID]
ICC1=ACS ACR38U-CCID 00 00

[ACR128U]
ICC1=ACS ACR128U 00 00
ICC2=ACS ACR128U 00 01
ICC3=ACS ACR128U 00 02



History
-------

v1.0.1 (23/1/2017)
- Based on v1.0.1.1 (Windows).

v1.0.0 (12/9/2011)
- New release.
- Based on v1.0.0.1 (Windows).



File Contents
-------------

API Documentation: ctacs.chm
Header Files:      include
Library Files:     lib



Support
-------

In case of problem, please contact ACS through:

Web Site: http://www.acs.com.hk/
E-mail: info@acs.com.hk
Tel: +852 2796 7873
Fax: +852 2796 1286



-------------------------------------------------------------------------------
Copyright (C) 2011-2017 Advanced Card Systems Ltd. All Rights Reserved.

Copyright
Copyright by Advanced Card Systems Ltd. (ACS) No part of this reference manual
may be reproduced or transmitted in any from without the expressed, written
permission of ACS.

Notice
Due to rapid change in technology, some of specifications mentioned in this
publication are subject to change without notice. Information furnished is
believed to be accurate and reliable. ACS assumes no responsibility for any
errors or omissions, which may appear in this document.
