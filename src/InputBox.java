import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InputBox extends Container implements KeyListener {
	Input i;
	String text = "";
	String header = "";
	Font f;
	Boolean done;
	Method okb;
	Object target;
	
	public static final int BACKSPACE = 14;
	public static final int RSHIFT = 56;
	public static final int LSHIFT = 42;
	public static final int ENTER = 28;
	private int titlebar_height;
	private Container titlebar;
	
	public InputBox(String text, int x, int y, Font f, double weight, Input i, Method okb, Object target) throws NoSuchMethodException, SecurityException {
		super(f.getWidth(text) + 80, f.getHeight(text) + 170, x, y, 0, 0, weight);
		this.i=i;
		this.i.addKeyListener(this);
		this.header = text;
		this.f = f;
		this.hidden = false;
		this.okb=okb;
		this.target=target;
		
		titlebar_height = f.getHeight(header) + 2;
		titlebar = new Container(this.sizex, titlebar_height, this.x, this.y, 2, 2, weight);
		
		Button exit = new Button(StateGame.f_24.getWidth("Submit") + 16, StateGame.f_24.getHeight("Submit") + 8, sizex / 2 - (StateGame.f_24.getWidth("Submit") + 16) / 2, 5 * sizey / 7 - (StateGame.f_24.getHeight("Ok") + 8) / 2, 2, "Ok", StateGame.f_24, fgetMethod("submit"), this);
		this.add_container(exit);

	}

	@Override
	public void finalize() {
		System.out.printf("shit");
		this.i.removeKeyListener(this);
	}
	
	public void submit() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		okb.invoke(target, text);
		destroy=true;
		this.i.removeKeyListener(this);
	}

	public String insert_char(String text, char ch, int idx) {
		int len = text.length();
		char[] new_array = new char[len + 1];
		text.getChars(0, idx, new_array, 0);
		new_array[idx] = ch;
		text.getChars(idx, len, new_array, idx + 1);
		return new String(new_array);
	}

	@Override
	public void draw(Graphics surface) throws SlickException {

		
		try {
			super.draw(surface);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		titlebar.draw(surface);
		surface.setColor(outer);
		f.drawString(x+2 ,y+2, header, outer);
		/* process string to see if it will extend beyond container */

		String print_str = text;

		int beg = 0;
		int i = 0;
		for (i = 0; i < print_str.length(); i++) {
			if (f.getWidth(print_str.substring(beg, i)) > sizex - 8) {
				print_str = insert_char(print_str, '\n', i);
				beg = i;
			}
		}

		String split_text[] = print_str.split("\n");
		for (int j = 0; j < split_text.length && (j + 1) * f.getHeight(split_text[j]) < sizey; j++) {
			f.drawString(x, y + (j * f.getHeight(split_text[j])) + titlebar_height, split_text[j]);
		}
		
		
	}

	@Override
	public void inputEnded() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputStarted() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isAcceptingInput() {
		return true;
	}

	@Override
	public void setInput(Input arg0) {
		this.i = arg0;
	}

	@Override
	public void keyPressed(int arg0, char arg1) {
		// backspace
		System.out.printf("pressed, %d %c\n", arg0, arg1);

		StringBuilder sb = new StringBuilder(text);
		switch (arg0) {
		case BACKSPACE:
			if (text.length() > 0) {
				sb.setLength(sb.length() - 1);
				text = sb.toString();
			}
			break;
		case RSHIFT:
		case LSHIFT:
			break;
		case ENTER:
			text += "\n";
			break;
		default:
			text += arg1;
		}
	}

	@Override
	public void keyReleased(int arg0, char arg1) {
		// TODO Auto-generated method stub

	}

}
