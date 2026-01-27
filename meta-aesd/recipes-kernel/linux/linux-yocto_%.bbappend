FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://kmemleak.cfg"

KERNEL_CONFIG_FRAGMENTS += "kmemleak.cfg"
