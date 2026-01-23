inherit module update-rc.d
SUMMARY = "LDD misc-modules kernel module"
DESCRIPTION = "Builds the "misc-modules" module from ldd3 assignment"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://faulty.c;beginline=1;endline=15;md5=0aea7b2495a9f764932337e1ce1d7674"

SRC_URI = "git://git@github.com/NehaP2605/assignment-7-NehaP2605.git;protocol=ssh;branch=main \
	   file://misc-modules-init \
           file://module_load \
           file://module_unload"
SRCREV = "59e580446ff92029b2f344088b864b26190562f5"

PV = "1.0+git${SRCPV}"
S = "${WORKDIR}/git/misc-modules"

EXTRA_OEMAKE += " -C ${STAGING_KERNEL_DIR} M=${S}"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "misc-modules-init"
INITSCRIPT_PARAMS:${PN} = "defaults 20"

do_compile() {
    # Compile only faulty.c and hello.c into kernel modules
    oe_runmake -C ${STAGING_KERNEL_DIR} M=${S} modules
    
}

do_install:append() {
    # Install the module
    module_do_install
    # Install scripts
    install -d ${D}/bin/misc-modules
    install -m 0755 ${WORKDIR}/module_load ${D}/bin/misc-modules/module_load
    install -m 0755 ${WORKDIR}/module_unload ${D}/bin/misc-modules/module_unload
    #Install the init script
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/misc-modules-init ${D}${sysconfdir}/init.d/misc-modules-init
}

FILES:${PN} += "/bin/misc-modules/module_load \
                /bin/misc-modules/module_unload \
                ${sysconfdir}/init.d/misc-modules-init"
