package com.active.teethtime.data

import com.active.teethtime.R
import com.active.teethtime.model.Step

class Datasource() {
    fun loadSteps(): List<Step>{
        return listOf<Step>(
            Step(R.string.instr1, R.drawable.t),
            Step(R.string.instr2, R.drawable.t2),
            Step(R.string.instr3, R.drawable.t3),
            Step(R.string.instr4, R.drawable.t4),
            Step(R.string.instr5, R.drawable.t5),
            Step(R.string.instr6, R.drawable.t6),

        )
    }
}