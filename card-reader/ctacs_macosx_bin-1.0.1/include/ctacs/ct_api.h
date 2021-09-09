/**
 * @file
 * ACS CT-API library header file.
 * @version   1.0.1
 * @date      20 January 2017
 * @copyright Copyright (C) 2011-2017 Advanced Card Systems Ltd. All rights reserved.
 */

/**
 * @mainpage Using CT-API
 *
 * @section Introduction Introduction
 *
 * This documentation covers the APIs provided by ACS CT-API library. This
 * library implements CT-API functions for ACS smart card readers.
 *
 * @section DefiningCardTerminals Defining Card Terminals
 *
 * To use CT-API with ACS smart card readers, you must place a initialization
 * file "ctacs.ini" to the current directory with your application program. This
 * file is to map PC/SC readers to card terminal numbers and ICC interfaces.
 *
 * The following sample INI file defines 2 card terminals. CTN1 is mapped to
 * ACR38U-CCID while CTN2 is mapped to ACR128U and each ICC interface is mapped
 * to PC/SC reader name.
 *
 * @subsection Windows Windows
 *
 * @code
 * ; Sample ctacs.ini (Windows)
 *
 * [CardTerminal]
 * CTN1=ACR38U-CCID
 * CTN2=ACR128U
 *
 * [ACR38U-CCID]
 * ICC1=ACS CCID USB Reader 0
 *
 * [ACR128U]
 * ICC1=ACS ACR128U ICC Interface 0
 * ICC2=ACS ACR128U PICC Interface 0
 * ICC3=ACS ACR128U SAM Interface 0
 * @endcode
 *
 * @subsection LinuxMacOSX Linux/Mac OS X
 *
 * @code
 * ; Sample ctacs.ini (Linux/Mac OS X)
 *
 * [CardTerminal]
 * CTN1=ACR38U-CCID
 * CTN2=ACR128U
 *
 * [ACR38U-CCID]
 * ICC1=ACS ACR38U-CCID 00 00
 *
 * [ACR128U]
 * ICC1=ACS ACR128U 00 00
 * ICC2=ACS ACR128U 00 01
 * ICC3=ACS ACR128U 00 02
 * @endcode
 *
 * @section CallingCTAPIFunctions Calling CT-API Functions
 *
 * Your source code must include a header file "ct_api.h" in order to call the
 * CT-API functions.
 *
 * @code
 * #include <stdio.h>
 * #include <ct_api.h>
 *
 * int main(int argc, char *argv[])
 * {
 *     char ret;
 *     unsigned short ctn;
 *     unsigned short pn;
 *     unsigned char sad;
 *     unsigned char dad;
 *
 *     // REQUEST ICC
 *     unsigned char command[] = { 0x20, 0x12, 0x01, 0x00, 0x00 };
 *     unsigned short lenc = sizeof(command);
 *
 *     unsigned char response[300];
 *     unsigned short lenr = sizeof(response);
 *     unsigned short i;
 *
 *     ctn = 1;
 *     pn = 1;
 *
 *     // Initialize card terminal
 *     ret = CT_init(ctn, pn);
 *     if (ret != OK)
 *     {
 *         printf("Error: CT_init failed with error %d\n", ret);
 *         return 1;
 *     }
 *
 *     sad = 2; // Source = Host
 *     dad = 1; // Destination = Card Terminal
 *
 *     // Send command
 *     ret = CT_data(ctn, &dad, &sad, lenc, command, &lenr, response);
 *     if (ret != OK)
 *         printf("Error: CT_data failed with error %d\n", ret);
 *     else
 *     {
 *         // Display response
 *         printf("Response: ");
 *         for (i = 0; i < lenr; i++)
 *             printf("%02X ", response[i]);
 *         printf("\n");
 *     }
 *
 *     // Close card terminal
 *     ret = CT_close(ctn);
 *     if (ret != OK)
 *         printf("Error: CT_close failed with error %d\n", ret);
 *
 *     return 0;
 * }
 * @endcode
 */

/**
 * @page ReturnValues Return Values
 *
 * @section CTAPIReturnValues CT-API Return Values
 *
 * CT-API functions return the following values:
 *
 * <table>
 * <tr><th>Error Code</th><th>Value</th><th>Description</th></tr>
 * <tr><td>OK</td><td>0</td><td>Function call was successful.</td></tr>
 * <tr><td>ERR_INVALID</td><td>-1</td><td>Invalid parameter or value.</td></tr>
 * <tr><td>ERR_CT</td><td>-8</td><td>CT error.</td></tr>
 * <tr><td>ERR_TRANS</td><td>-10</td><td>Non-eliminable transmission error.</td></tr>
 * <tr><td>ERR_MEMORY</td><td>-11</td><td>Memory assignment error in HTSI.</td></tr>
 * <tr><td>ERR_HOST</td><td>-127</td><td>Abort of function by host/OS.</td></tr>
 * <tr><td>ERR_HTSI</td><td>-128</td><td>HTSI error.</td></tr>
 * </table>
 */

#ifndef CT_API_H
#define CT_API_H

#ifdef _WIN32
#include <windows.h>
#else
#define WINAPI  ///< Calling convention.
#endif

// Error codes
#define OK              0       ///< Function call was successful.
#define ERR_INVALID     -1      ///< Invalid parameter or value.
#define ERR_CT          -8      ///< CT error.
#define ERR_TRANS       -10     ///< Non-eliminable transmission error.
#define ERR_MEMORY      -11     ///< Memory assignment error in HTSI.
#define ERR_HOST        -127    ///< Abort of function by host/OS.
#define ERR_HTSI        -128    ///< HTSI error.

/**
 * Pointer to CT_init() function.
 */
typedef char (WINAPI *CT_INIT)(unsigned short ctn, unsigned short pn);

/**
 * Pointer to CT_data() function.
 */
typedef char (WINAPI *CT_DATA)(unsigned short ctn, unsigned char *dad, unsigned char *sad,
    unsigned short lenc, unsigned char *command, unsigned short *lenr, unsigned char *response);

/**
 * Pointer to CT_close() function.
 */
typedef char (WINAPI *CT_CLOSE)(unsigned short ctn);

#ifdef __cplusplus
extern "C" {
#endif

/**
 * Initiation of the host/CT connection.
 * @param ctn Logical CardTerminal number.
 * @param pn  Port number of the physical interface. This parameter is reserved
 *            for future use and it must be 1.
 * @return If the function succeeds, the function returns OK.<br />
 *         If the function fails, it returns an error code. For more
 *         information, see @ref CTAPIReturnValues.
 */
char WINAPI CT_init(unsigned short ctn, unsigned short pn);

/**
 * Transmission of a command to a CardTerminal or to an ICC and give back the
 * response.
 * @param ctn      Logical CardTerminal number.
 * @param dad      Destination address.
 * <table>
 * <tr><th>Destination Address (Hex)</th><th>Receiver</th></tr>
 * <tr><td>00</td><td>ICC1 (IC card 1)</td></tr>
 * <tr><td>01</td><td>CT</td></tr>
 * <tr><td>02</td><td>ICC2 (IC card 2)</td></tr>
 * <tr><td>...</td><td>...</td></tr>
 * <tr><td>0E</td><td>ICC14 (IC card 14)</td></tr>
 * <tr><td>XX</td><td>other values reserved</td></tr>
 * </table><br />
 * @param sad      Source address.
 * <table>
 * <tr><th>Source Address (Hex)</th><th>Sender</th></tr>
 * <tr><td>02</td><td>HOST</td></tr>
 * <tr><td>05</td><td>REMOTE HOST</td></tr>
 * </table><br />
 * @param lenc     Command length in byte.
 * @param command  ICC-command or CT-command.
 * @param lenr     Passing of the max. buffer size of the response field to the
                   function and return of the actual length of the response in
                   byte.
 * @param response Response to the command.
 * @return If the function succeeds, the function returns OK.<br />
 *         If the function fails, it returns an error code. For more
 *         information, see @ref CTAPIReturnValues.
 */
char WINAPI CT_data(unsigned short ctn, unsigned char *dad, unsigned char *sad,
    unsigned short lenc, unsigned char *command, unsigned short *lenr, unsigned char *response);

/**
 * Close the host/CT connection.
 * @param ctn Logical CardTerminal number.
 * @return If the function succeeds, the function returns OK.<br />
 *         If the function fails, it returns an error code. For more
 *         information, see @ref CTAPIReturnValues.
 */
char WINAPI CT_close(unsigned short ctn);

#ifdef __cplusplus
}
#endif

#endif
