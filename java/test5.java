import com.megain.junhao.util.TimeUtils;

public class test5 {
    public static void main(String[] args) {
        String s= "Africa/Cairo(UTC+02:00),Africa/Casablanca(UTC+01:00),Africa/Harare(UTC+02:00),Africa/Monrovia(UTC+00:00),Africa/Nairobi(UTC+03:00),Africa/Tripoli(UTC+02:00),Africa/Windhoek(UTC+01:00),America/Araguaina(UTC-03:00),America/Asuncion(UTC-04:00),America/Bogota(UTC-05:00),America/Caracas(UTC-04:00),America/Chihuahua(UTC-06:00),America/Cuiaba(UTC-04:00),America/Denver(UTC-06:00),America/Fortaleza(UTC-03:00),America/Guatemala(UTC-06:00),America/Halifax(UTC-03:00),America/Manaus(UTC-04:00),America/Matamoros(UTC-05:00),America/Monterrey(UTC-05:00),America/Montevideo(UTC-03:00),America/Phoenix(UTC-07:00),America/Santiago(UTC-04:00),America/Tijuana(UTC-07:00),Asia/Amman(UTC+03:00)+2,Asia/Ashgabat(UTC+05:00),Asia/Baghdad(UTC+03:00),Asia/Baku(UTC+04:00),Asia/Bangkok(UTC+07:00),Asia/Beirut(UTC+03:00),Asia/Calcutta(UTC+05:30),Asia/Damascus(UTC+03:00),Asia/Dhaka(UTC+06:00),Asia/Irkutsk(UTC+08:00),Asia/Jerusalem(UTC+03:00),Asia/Kabul(UTC+04:30),Asia/Karachi(UTC+05:00),Asia/Kathmandu(UTC+05:45),Asia/Krasnoyarsk(UTC+07:00),Asia/Magadan(UTC+11:00),Asia/Muscat(UTC+04:00),Asia/Novosibirsk(UTC+06:00),Asia/Riyadh(UTC+03:00),Asia/Seoul(UTC+09:00),Asia/Shanghai(UTC08:00),Asia/Singapore(UTC+08:00),Asia/Taipei(UTC+08:00),Asia/Tehran(UTC+04:30),Asia/Tokyo(UTC+09:00),Asia/Ulaanbaatar(UTC+09:00),Asia/Vladivostok(UTC+10:00),Asia/Yakutsk(UTC+09:00),Asia/Yerevan(UTC+04:00),Atlantic/Azores(UTC+00:00),Australia/Adelaide(UTC+09:30),Australia/Brisbane(UTC+10:00),Australia/Darwin(UTC+09:30),Australia/Hobart(UTC+10:00),Australia/Perth(UTC+08:00),Australia/Sydney(UTC+10:00),Canada/Newfoundland(UTC-02:30),Canada/Saskatchewan(UTC-06:00),Brazil/East(UTC-03:00),Europe/Amsterdam(UTC+02:00),Europe/Athens(UTC+03:00),Europe/Dublin(UTC+01:00),Europe/Helsinki(UTC+03:00),Europe/Istanbul(UTC+03:00),Europe/Kaliningrad(UTC+02:00),Europe/Moscow(UTC+03:00),Europe/Paris(UTC+02:00),Europe/Prague(UTC+02:00),Europe/Sarajevo(UTC+02:00),Pacific/Auckland(UTC+12:00),Pacific/Fiji(UTC+12:00),Pacific/Guam(UTC+10:00),Pacific/Honolulu(UTC-10:00),Pacific/Samoa(UTC-11:00),US/Alaska(UTC-08:00),US/Central(UTC-05:00),US/Eastern(UTC-04:00),US/East-Indiana(UTC-04:00),US/Pacific(UTC-07:00),UTC(UTC+00:00),SYSTEM(UTC+00:00)";
        String[] newStr = s.split(",");	// 分割成数组

        for (String string : newStr) {
            System.out.println(string);	// 输出
        }


        long time = System.currentTimeMillis();
        System.out.println("当前时间为： " + TimeUtils.getCurrentDatetime() + "        " + "时间戳： " + time + "           ");
    }
}
