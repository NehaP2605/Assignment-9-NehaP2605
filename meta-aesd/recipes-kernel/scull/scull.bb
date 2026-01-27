SUMMARY = "LDD scull kernel module"
DESCRIPTION = "Builds the scull module from ldd3 assignment"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://main.c;beginline=1;endline=15;md5=ca6299e5fabd9147265e88bd5220b7e5"

SRC_URI = "git://git@github.com/NehaP2605/assignment-7-NehaP2605.git;protocol=ssh;branch=main \
	   file://scull-init \
           file://scull_load \
           file://scull_unload"
SRCREV = "59e580446ff92029b2f344088b864b26190562f5"

PV = "1.0+git${SRCPV}"
S = "${WORKDIR}/git/scull"

inherit module update-rc.d

EXTRA_OEMAKE += " -C ${STAGING_KERNEL_DIR} M=${S}"


INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "scull-init"
INITSCRIPT_PARAMS:${PN} = "defaults 20"

do_compile() {
    # Build only scull module
    oe_runmake -C ${STAGING_KERNEL_DIR} M=${S} modules
}

do_install:append() {
    # Install the module
    module_do_install
    # Install scripts
    install -d ${D}${bindir}/scull
    install -m 0755 ${WORKDIR}/scull_load ${D}${bindir}/scull/scull_load
    install -m 0755 ${WORKDIR}/scull_unload ${D}${bindir}/scull/scull_unload
    #Install the init script
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/scull-init ${D}${sysconfdir}/init.d/scull-init
}

FILES:${PN} += "${sysconfdir}/init.d/scull-init \
                ${bindir}/scull/scull_load \
                ${bindir}/scull/scull_unload"
