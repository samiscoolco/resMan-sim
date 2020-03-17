import java.lang.reflect.InvocationTargetException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class EntityWindow extends Window {

	Entity selected_ent;
	StateGame s;
	
	/* human attrs */
	Container human;
	
	HorzBarGraph hp_bar;
	Label lvl_num;
	Label age_num;

	public EntityWindow(StateGame s) throws NoSuchMethodException, SecurityException, SlickException {
		super(300, 350, 100, 100, Game.win_pad, Game.win_pad, 2,"NULL", s);

		this.selected_ent = null;
		
		build_human_cont();
		
		/* temporary */
		add_container(human);
		
		for (Container c: containers) {
			c.set_theme(Game.clear, Game.win_outer);
		}
	}
	
	public void build_human_cont() {
		/* hp */
		Label hp = new Label(0, 0, 0, 0, 0, "HP  ", s.f_18);		
		hp_bar = new HorzBarGraph(100, hp.sizey, hp.sizex + padx, 0, 2);
		Container hp_cont = new Container((int)hp_bar.relx + hp_bar.sizex, 
				hp.sizey, 0, 0, 0, 0, 0);
		hp_cont.add_container(hp, hp_bar);
		
		/* level */
		Label lvl = new Label(0, 0, 0, 0, 0, "LVL ", s.f_18);
		lvl_num = new Label(lvl.sizex + padx, 0, 0, 0, 0, "0", s.f_18);
		Container lvl_cont = new Container((int)lvl_num.relx + lvl_num.sizex, lvl_num.sizey, 
				0, hp_cont.rely + hp_cont.sizey + pady, 0, 0, 0);
		lvl_cont.add_container(lvl, lvl_num);
		
		/* age */
		Label age = new Label(0, 0, 0, 0, 0, "AGE ", s.f_18); 
		age_num = new Label(age.sizex + padx, 0, 0, 0, 0, "NULL", s.f_18);
		Container age_cont = new Container(age.sizex + padx + age_num.sizex , age.sizey, 0, 
				lvl_cont.rely + lvl_cont.sizey + pady, 0, 0, 0);
		age_cont.add_container(age, age_num);
		
		human = new Container(sizex, hp_cont.sizey + lvl_cont.sizey + age.sizey, -padx, -pady, padx, pady, 0);
		human.add_container(hp_cont, lvl_cont, age_cont);
	}
	
	public void update(Input i, int mx, int my, int delta) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super.update(i, mx, my, delta);
		update_human();
	}
	
	public void update_human() {
		hp_bar.percent += 0.0001f;
		hp_bar.percent = hp_bar.percent > 1 ? 0 : hp_bar.percent;
	}
}
