package org.md2k.library.phonesensor.sensor.step_counter;

/*
 * Copyright (c) 2016, The University of Memphis, MD2K Center
 * - Syed Monowar Hossain <monowar.hossain@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class MCStepCounter /*extends AbstractNativeSensor*/ {

/*
    public StepCounter(Context context) {
        super(context, Sensor.TYPE_STEP_COUNTER);
        setReadFrequency(6, TimeUnit.SECONDS);
        setWriteFixed(6, TimeUnit.SECONDS);
    }

    @Override
    public SensorInfo getSensorInfo() {
        return new SensorInfo() {
            @Override
            public String getId(){
                return SensorType.STEP_COUNTER.name();
            }
            @Override
            public HashMap<String, String> getInfo() {
                return StepCounter.super.getSensorInfo(Sensor.TYPE_STEP_COUNTER);
            }
            @Override
            public String getName() {
                return "Step Counter";
            }

            @Override
            public String getDescription() {
                return "Measures the acceleration force in g that is applied to a device on all three physical axes (x, y, and z), including the force of gravity";
            }
        };
    }

    @Override
    public DataDescriptor getDataDescriptor() {
        return new StepCounterDataDescriptor();
    }
*/

}
