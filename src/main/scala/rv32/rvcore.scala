//=======================================================================
// RISCV, A Simple As Possible Core
// Watson Huang
// Dec 26, 2016
// 
// Combine control path (cpath) and data path (dpath) into a core
//=======================================================================
package rvcore

import chisel3._
import chisel3.util._
import rvcommon._

class core_io extends Bundle {
    val imem = new mram_io(rvspec.xlen)
    val dmem = new mram_io(rvspec.xlen)
    val rfdbg = Flipped(new mram_io(rvspec.xlen))
}

class rvcore extends Module {
    val io = IO(new core_io)

    val cpath = Module(new rvcpath())
    val dpath = Module(new rvdpath())

    cpath.io.imem <> io.imem
    cpath.io.dmem <> io.dmem

    dpath.io.imem <> io.imem
    dpath.io.dmem <> io.dmem
    dpath.io.rfdbg <> io.rfdbg

    cpath.io.c2d <> dpath.io.c2d
    dpath.io.d2c <> cpath.io.d2c
}