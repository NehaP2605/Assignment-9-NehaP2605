SUMMARY = "aesdchar kernel module"
DESCRIPTION = "Builds the aesdchar module for assignment8"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit module update-rc.d

SRC_URI = "git://git@github.com/NehaP2605/assignment-3-NehaP2605-.git;protocol=ssh;branch=main \
           file://aesdchar-init \
           file://aesdchar_load \
           file://aesdchar_unload"

SRCREV = "fd4a0fbeb78262ac583997bd997be73463e1bfde"

PV = "1.0+git${SRCPV}"
S = "${WORKDIR}/git/aesd-char-driver"


INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdchar-init"
INITSCRIPT_PARAMS:${PN} = "defaults 20"

EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"
EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"

do_install:append() {
    # Install scripts
    install -d ${D}${bindir}/aesdchar
    install -m 0755 ${WORKDIR}/aesdchar_load ${D}${bindir}/aesdchar/aesdchar_load
    install -m 0755 ${WORKDIR}/aesdchar_unload ${D}${bindir}/aesdchar/aesdchar_unload
    #Install the init script
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/aesdchar-init ${D}${sysconfdir}/init.d/aesdchar-init
}

FILES:${PN} += "${sysconfdir}/init.d/aesdchar-init \
                ${bindir}/aesdchar/aesdchar_load \
                ${bindir}/aesdchar/aesdchar_unload"
