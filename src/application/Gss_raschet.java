package application;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;


class Gss_raschet {
	private int dav = 0;
	private int bal = 1;
	private Calendar cal;
	private int ob_bal = 0;
	private final int rez = 55;
	private int ras = 40;
	public int dav_ob = 0;
	private SimpleDateFormat forma = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	
	public int getDav() {
		return dav;
	}
	public void setDav(int dav) {
		this.dav = dav;
	}
	public int getBal() {
		return bal;
	}
	public void setBal(int bal) {
		this.bal = bal;
	}
	public Calendar getCal() {
		return cal;
	}
	public void setCal(Calendar cal) {
		this.cal = cal;
	}
	public int getOb_bal() {
		return ob_bal;
	}
	public void setOb_bal(int ob_bal) {
		this.ob_bal = ob_bal;
	}
	
	public int dav_Vihoda () {
		int Pvih;
		Pvih = ((dav-rez)/3)*2+55;
		return Pvih;
	}
	
	public int t_obshee() {
		int t_obshee;
		double d = (((double) (dav - rez))*bal*ob_bal)/ras;
		t_obshee = new BigDecimal(d).setScale(0, RoundingMode.HALF_UP).intValue();
		return t_obshee;
	}
	
	public int dvig() {
		int dvig;
		dvig = new BigDecimal (((double)t_obshee())/3).setScale(0, RoundingMode.HALF_UP).intValue();
		return dvig;
	}
	
	public int dav_raz() {
		return dav-dav_ob;
	}
	
	public int dav_vihoda2() {
		int vihod;
		vihod = (dav_raz()*2)+rez;
		return vihod;
	}
	
	public int t_rabot () {
		double g;
		g =  (double) ( dav-(dav_raz()+dav_vihoda2() ));
		double t = (g*bal*ob_bal)/ras;
		int i = new BigDecimal(t).setScale(0, RoundingMode.HALF_UP).intValue();
		return i;
	}
	
	public String gss_date() {
		cal = new GregorianCalendar(); // создаем календарь григорианский
		String s = forma.format(cal.getTime()); // запрашиваем текущую дату и приводим ее к нужному нам формату (forma)
		return s;
	}
	/*
	 * public void taime (int t_rabot) {
		Timer timer = new Timer();
		int m1 = t_rabot;
		TimerTask timerT = new TimerTask() {
			
			@Override
			public void run() {
				
				// TODO Auto-generated method stub
			}
			
		};
	}
*/

}
