#!/bin/sh

#
# Copyright (C) 2011-2017 Advanced Card Systems Ltd. All Rights Reserved.
#
# This software is the confidential and proprietary information of Advanced
# Card Systems Ltd. ("Confidential Information").  You shall not disclose such
# Confidential Information and shall use it only in accordance with the terms
# of the license agreement you entered into with ACS.
#

#
# install.sh
# This script is to install the library.
#

USER=`whoami`

if [ $USER != root ]; then
    echo "You must be root to run this script."
    exit 1
fi

echo "Installing libctacs..."
// On Big Sur the directories are /usr/local/include and /usr/local/lib
cp -pPRv include/* /usr/local/include
cp -pPRv lib/* /usr/local/lib


#cp -pPRv include/* /usr/include
#cp -pPRv lib/* /usr/lib

echo "Completed!"
