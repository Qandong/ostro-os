SUMMARY = "ModemManager is a daemon controlling broadband devices/connections"
DESCRIPTION = "ModemManager is a DBus-activated daemon which controls mobile broadband (2G/3G/4G) devices and connections"
HOMEPAGE = "http://www.freedesktop.org/wiki/Software/ModemManager/"
LICENSE = "GPL-2.0 & LGPL-2.1"
LIC_FILES_CHKSUM = " \
    file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://COPYING.LIB;md5=4fbd65380cdd255951079008b364516c \
"

inherit gnomebase gettext systemd vala gobject-introspection bash-completion

DEPENDS = "glib-2.0 libgudev dbus-glib intltool-native"

SRC_URI = "http://www.freedesktop.org/software/ModemManager/ModemManager-${PV}.tar.xz \
           file://enum-conversion.patch \
"
SRC_URI[md5sum] = "06488186c7dd53f8104183b86f7a1568"
SRC_URI[sha256sum] = "cdd5b4cb1e4d7643643a28ccbfc4bb354bfa9cb89a77ea160ebdf7926171c668"

S = "${WORKDIR}/ModemManager-${PV}"

PACKAGECONFIG ??= "mbim qmi polkit \
    ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'systemd', '', d)} \
"

PACKAGECONFIG[systemd] = "--with-systemdsystemunitdir=${systemd_unitdir}/system/,,"
PACKAGECONFIG[polkit] = "--with-polkit=yes,--with-polkit=no,polkit"
# Support WWAN modems and devices which speak the Mobile Interface Broadband Model (MBIM) protocol.
PACKAGECONFIG[mbim] = "--with-mbim,--enable-mbim=no,libmbim"
# Support WWAN modems and devices which speak the Qualcomm MSM Interface (QMI) protocol.
PACKAGECONFIG[qmi] = "--with-qmi,--without-qmi,libqmi"

FILES_${PN} += " \
    ${datadir}/icons \
    ${datadir}/polkit-1 \
    ${datadir}/dbus-1 \
    ${libdir}/ModemManager \
    ${systemd_unitdir}/system \
"

FILES_${PN}-dev += " \
    ${libdir}/ModemManager/*.la \
"

FILES_${PN}-staticdev += " \
    ${libdir}/ModemManager/*.a \
"

FILES_${PN}-dbg += "${libdir}/ModemManager/.debug"

SYSTEMD_SERVICE_${PN} = "ModemManager.service"
