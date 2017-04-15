package com.ml.man;

import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LevelDiff extends Frame {

	CheckboxGroup group;
	Checkbox box1;
	Checkbox box2;
	Checkbox box3;

	public LevelDiff(String title) {
		super(title);
		this.setBackground(Color.PINK);
		this.setBounds(500, 250, 200, 100);

		this.setLayout(new GridLayout(1, 3));

		group = new CheckboxGroup();

		box1 = new Checkbox("低级", group, true);
		box2 = new Checkbox("中级", group, false);
		box3 = new Checkbox("高级", group, false);

		this.add(box1);
		this.add(box2);
		this.add(box3);

		MyaddItemListener listener = new MyaddItemListener();

		box1.addItemListener(listener);
		box2.addItemListener(listener);
		box3.addItemListener(listener);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				MyFrame.mf.frameFlag = false;
			}
		});

		this.setVisible(false);
		this.setResizable(false);

	}

	class MyaddItemListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			Checkbox box = (Checkbox) e.getSource();
			if (box == box1) {
				MyFrame.mf.speed = 500;
				MyFrame.mf.l2.setText("难度：低级");
			}
			if (box == box2) {
				MyFrame.mf.speed = 200;
				MyFrame.mf.l2.setText("难度：中级");
			}
			if (box == box3) {
				MyFrame.mf.speed = 100;
				MyFrame.mf.l2.setText("难度：高级");
			}
		}

	}
}
