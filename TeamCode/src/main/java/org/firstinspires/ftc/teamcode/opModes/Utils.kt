package org.firstinspires.ftc.teamcode.opModes

import com.acmerobotics.roadrunner.InstantFunction
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo

class Utils {

    public class Launcher(hardwareMap: HardwareMap, val sleep: (Long) -> Unit) : InstantFunction {
        private val pusher: Servo = hardwareMap.get(Servo::class.java, "push")


        init {
            pusher.position = 0.0
        }

        override fun run() {
            pusher.position = 0.5
            sleep(100)
            pusher.position = 0.0
        }


    }


}