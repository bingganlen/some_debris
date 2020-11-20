package com.megain.nfctemp.utils.pdf

import android.content.Context
import com.megain.label.driver.jni.THDecoder
import com.megain.label.driver.label.Label
import com.megain.label.driver.label.LabelSetting
import com.megain.label.driver.util.FloatDoubleUtil
import com.megain.nfctemp.R
import com.megain.nfctemp.jni.MathCalculate
import com.megain.nfctemp.main.CurApp
import com.megain.nfctemp.model.ThSetting
import com.megain.nfctemp.utils.SettingsSpProxy
import com.megain.nfctemp.utils.TemRhUtil
import com.megain.nfctemp.utils.TimeStringUtil
import java.util.*

object TempRhPdfSource {

    fun buildSettingInfo(label: Label, labelSetting: LabelSetting, order: ThSetting.Order?): List<String> {
        val labelId: String = label.getId()
        val pdfLines = ArrayList<String>()
        val firmwareInfo = label.getFirmwareInfo()
        val context = CurApp.getAppContext()
        pdfLines.add(context.getString(R.string.csvCardSettingTitle))
        pdfLines.add(context.getString(R.string.CardId) + " " + labelId)
        pdfLines.add(context.getString(R.string.search_label_config_date)+" "+TimeStringUtil.getDisplayTime(labelSetting.createdTime.time))
        if (order != null) {
            pdfLines.add(context.getString(R.string.waybillNB) + context.getString(R.string.colon) + " " + order.number)
            pdfLines.add(context.getString(R.string.waybillTime) + " " + order.waybillTime)
            pdfLines.add(context.getString(R.string.companyName) + " " + order.companyName)
            pdfLines.add(context.getString(R.string.produceName) + " " + order.produceName)
            pdfLines.add(context.getString(R.string.waybillCreator) + " " + order.creator)
            pdfLines.add(context.getString(R.string.shipAddress) + " " + order.sendAddr)
            pdfLines.add(context.getString(R.string.receiptAddress) + " " + order.receiptAddr)
        } else {
            pdfLines.add(context.getString(R.string.waybillNB) + context.getString(R.string.colon) + " ")
            pdfLines.add(context.getString(R.string.waybillTime) + " ")
            pdfLines.add(context.getString(R.string.companyName) + " ")
            pdfLines.add(context.getString(R.string.produceName) + " ")
            pdfLines.add(context.getString(R.string.waybillCreator) + " ")
            pdfLines.add(context.getString(R.string.shipAddress) + " ")
            pdfLines.add(context.getString(R.string.receiptAddress) + " ")
        }
        val tempUnit = context.getString(if (SettingsSpProxy.get().isCentigrade) R.string.tempUnit else R.string.tempFahrenheitUnit)
        val maxTemp = if (SettingsSpProxy.get().isCentigrade) labelSetting.maxTemp else SettingsSpProxy.TempUtil.toFahrenheit(labelSetting.maxTemp)
        val minTemp = if (SettingsSpProxy.get().isCentigrade) labelSetting.minTemp else SettingsSpProxy.TempUtil.toFahrenheit(labelSetting.minTemp)
        pdfLines.add(context.getString(R.string.delayTime) + " " + labelSetting.delayTime + context.getString(R.string.min))
        pdfLines.add(context.getString(R.string.intervalSamp) + context.getString(R.string.colon) + " " + labelSetting.sampleInterval + context.getString(R.string.min))
        pdfLines.add(context.getString(R.string.MaxTemp) + context.getString(R.string.colon) + " " + maxTemp + tempUnit)
        pdfLines.add(context.getString(R.string.MinTemp) + context.getString(R.string.colon) + " " + minTemp + tempUnit)
        if (labelSetting.thAttribute == LabelSetting.THAttribute.TEMP_HUMIDITY) {
            pdfLines.add(context.getString(R.string.maxHumidity) + context.getString(R.string.colon) + " " + labelSetting.maxHumidity.toInt() + "%RH")
            pdfLines.add(context.getString(R.string.minHumidity) + context.getString(R.string.colon) + " " + labelSetting.minHumidity.toInt() + "%RH")
        }
        pdfLines.add(context.getString(R.string.remarkInfo) + " " + if (order != null) order.remark else "")
        pdfLines.add("")
        pdfLines.add(context.getString(R.string.firmwareInfo))
        pdfLines.add(context.getString(R.string.firmVersion) + " " + firmwareInfo?.remoteFirmwareVersion)
        pdfLines.add(context.getString(R.string.maxRoom) + " " + firmwareInfo?.flashCapacity + "KB")
        pdfLines.add(context.getString(R.string.transmit) + " " + firmwareInfo?.transmitVelocity + "kpbs")
        pdfLines.add(context.getString(R.string.hardwareVersion) + " " + firmwareInfo?.hardwareVersion + " ")
        pdfLines.add(context.getString(R.string.electricity) + " " + label.getLastPower() + "V")
        pdfLines.add(context.getString(R.string.stcFirmwareVersion) + " " + firmwareInfo?.stcFirmwareVersion + " ")
        pdfLines.add("")
        return pdfLines
    }

    fun buildRecordInfo(label: Label, labelSetting: LabelSetting): List<String> {
        val context = CurApp.getAppContext()
        val pdfLines = ArrayList<String>()

        pdfLines.add(context.getString(R.string.pdf_start_record_date) + " " + TimeStringUtil.getDisplayTime(labelSetting.bootTime.time))
        pdfLines.add(context.getString(R.string.pdf_last_record_date) + " " + TimeStringUtil.getDisplayTime(labelSetting.stopTime.time))
        pdfLines.add(context.getString(R.string.pdf_export_date) + " " + TimeStringUtil.getDisplayTime(Date(System.currentTimeMillis())))
        pdfLines.add(context.getString(R.string.pdf_all_record_time) + " " + toDisplay(getHumTime(labelSetting), context))
        pdfLines.add(context.getString(R.string.pdf_record_amount) + " " + labelSetting.thValues.tempValueAmount + " " + context.getString(R.string.pdf_record_amount_unit))
        pdfLines.add(context.getString(R.string.pdf_label_type) + " " + if (label.isNfcLabel()) "NFC" else "BLE")
        val sampleInterval = labelSetting.sampleInterval.toInt()
        val temps = TemRhUtil.getTem(labelSetting)
        if (temps != null) {
            val rawTempMax = THDecoder.get().max(temps, 0)
            val tawTmpMin = THDecoder.get().min(temps, 0)
            val cumTemMax = MathCalculate.get().getUpTempTotalTime(temps, labelSetting.maxTemp, sampleInterval, 0)
            val cumTemMin = MathCalculate.get().getDownTempTotalTime(temps, labelSetting.minTemp, sampleInterval, 0)
            val isCentigrade = SettingsSpProxy.get().isCentigrade
            val tempUnit = context.getString(if (isCentigrade) R.string.tempUnit else R.string.tempFahrenheitUnit)
            val tempMax = if (isCentigrade) rawTempMax else SettingsSpProxy.TempUtil.toFahrenheit(rawTempMax)
            val tempMin = if (isCentigrade) tawTmpMin else SettingsSpProxy.TempUtil.toFahrenheit(tawTmpMin)
            pdfLines.add(context.getString(R.string.pdf_highest_temp) + " " + "${FloatDoubleUtil.scaleOne(tempMax)}$tempUnit")
            pdfLines.add(context.getString(R.string.pdf_lowest_temp) + " " + "${FloatDoubleUtil.scaleOne(tempMin)} $tempUnit")
            pdfLines.add(context.getString(R.string.pdf_over_temp_limit) + " " + cumTemMax + " " + context.getString(R.string.min))
            pdfLines.add(context.getString(R.string.pdf_under_temp_limit) + " " + cumTemMin + " " + context.getString(R.string.min))
        }

        val rhs: IntArray? = TemRhUtil.getRh(labelSetting)
        if (rhs != null) {
            val humMax = THDecoder.get().maxInt(rhs, 0).toFloat()
            val humMin = THDecoder.get().minInt(rhs, 0).toFloat()
            val cumHumMax = MathCalculate.get().getUpRhTotalTime(rhs, labelSetting.maxHumidity.toInt(), sampleInterval, 0)
            val cumHumMin = MathCalculate.get().getDownRhTotalTime(rhs, labelSetting.minHumidity.toInt(), sampleInterval, 0)

            pdfLines.add(context.getString(R.string.pdf_highest_humidity) + " " + "${humMax.toInt()} %RH")
            pdfLines.add(context.getString(R.string.pdf_lowest_humidity) + " " + "${humMin.toInt()} %RH")
            pdfLines.add(context.getString(R.string.pdf_over_hum_limit) + " " + cumHumMax + " " + context.getString(R.string.min))
            pdfLines.add(context.getString(R.string.pdf_under_hum_limit) + " " + cumHumMin + " " + context.getString(R.string.min))
        }
        return pdfLines
    }

    private fun getHumTime(labelSetting: LabelSetting): Int {
        val thValues = labelSetting.thValues
        return (thValues.tempValueAmount - 1) * labelSetting.sampleInterval
    }

    private fun toDisplay(minute: Int, context: Context): String {
        return when {
            minute / 60 > 24 -> {
                val day = minute / 1440
                val hours = (minute - day * 1440) / 60
                val minutes = minute - day * 1440 - hours * 60
                day.toString() + context.getString(R.string.day) +
                        (if (hours > 0) " " + hours.toString() + context.getString(R.string.hour) else "") +
                        if (minutes > 0) " " + minutes.toString() + context.getString(R.string.min) else ""
            }
            minute / 60 in 1..23 -> {
                val hours = minute / 60
                val minutes = minute - hours * 60
                hours.toString() + context.getString(R.string.hour) +
                        if (minutes > 0) " " + minutes.toString() + context.getString(R.string.min) else ""
            }
            else -> " " + minute.toString() + context.getString(R.string.min)
        }
    }
}