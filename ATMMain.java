import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Bank
{
	long acc;
	int pin;
	float bal;
	Bank()
	{
		acc = 0;pin = 0;bal = 0.0f;
	}
	Bank(long a,int p,float b)
	{
		acc = a;
		pin = p;
		bal = b;
	}
	boolean deposit(float d)
	{
		if(d > 0.0f)
		{
			bal += d;
			return true;
		}
		else
		return false;
	}
	int withdraw(float w)
	{
		if(w > 0.0f && w < bal)
		{
			bal -= w;
			return 1;
		}
		else if(w > bal)
		return 0;
		else
		return -1;
	}
	int fund(long a,float f)
	{
		if(BankGlobal.search(a,0) >= -1)
		{
			if(BankGlobal.accounts.get(BankGlobal.fin).deposit(f) && withdraw(f)==1)
			return 1;
			else
			return 0;
		}
		else
		return -1;
	}
}

class BankGlobal
{
	public static ArrayList<Bank> accounts = new ArrayList<Bank>();
	public static int in = 0,fin = -1;
	public static int search(long a,int p)
	{
		int i;
		for(i=0;i < accounts.size();i++)
		{
			if(a == accounts.get(i).acc)
			{
				fin = i;
				break;
			}
		}
		if(i < accounts.size())
		{
			if(accounts.get(i).pin == p)
			return in=i;
			else
			return -1;
		}
		else
		return -2;
	}
}

class ATMInterface implements ActionListener
{
	JFrame f;
	JPanel cp,p1,p2,p3;
	JButton bw,bd,bbal,be,bs,bf,bb;
	JLabel lt,lc;
	JTextField tfb,tfa;
	int ch = 0;
	ATMInterface()
	{
		f = new JFrame("ATM");
		f.setSize(500,500);
		cp = new JPanel(new CardLayout());
		p1 = new JPanel(null);
		Font ft = new Font("Times New Roman",Font.BOLD,30);
		lt = new JLabel("ATM");
		lt.setFont(ft);
		lt.setBounds(215,20,70,30);
		bw = new JButton("Withdraw");
		bw.setBounds(75,100,150,30);
		bw.addActionListener(this);
		bd = new JButton("Deposit");
		bd.setBounds(250,100,150,30);
		bd.addActionListener(this);
		bbal = new JButton("Balance");
		bbal.setBounds(75,150,150,30);
		bbal.addActionListener(this);
		bf = new JButton("Fund Transfer");
		bf.setBounds(250,150,150,30);
		bf.addActionListener(this);
		be = new JButton("Exit");
		be.setBounds(200,200,100,30);
		be.addActionListener(this);
		bb = new JButton("Back");
		bb.setBounds(10,20,70,30);
		bb.addActionListener(this);
		p1.add(lt);
		p1.add(bw);
		p1.add(bd);
		p1.add(bbal);
		p1.add(bf);
		p1.add(be);
		p1.add(bb);
		cp.add(p1,"p1");
		f.add(cp);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	public void actionPerformed(ActionEvent ae)
	{
		CardLayout cl = (CardLayout)(cp.getLayout());
		if(ae.getActionCommand().equals("Deposit"))
		{
			ch = 1;
			dwp();
			cl.show(cp,"p2");
		}
		else if(ae.getActionCommand().equals("Withdraw"))
		{
			ch = -1;
			dwp();
			cl.show(cp,"p2");
		}
		else if(ae.getActionCommand().equals("Balance"))
		{
			JOptionPane.showMessageDialog(f,"Balance : " + String.valueOf(BankGlobal.accounts.get(BankGlobal.in).bal),"Balance",JOptionPane.INFORMATION_MESSAGE);
		}
		else if(ae.getActionCommand().equals("Fund Transfer"))
		{
			ch = -2;
			ftp();
			cl.show(cp,"p3");
		}
		else if(ae.getActionCommand().equals("Exit"))
		{
			System.exit(0);
		}
		else if(ae.getActionCommand().equals("Submit"))
		{
			float b = Float.parseFloat(tfb.getText());
			if(ch == 1)
			{
				if(BankGlobal.accounts.get(BankGlobal.in).deposit(b))
				JOptionPane.showMessageDialog(f,"Deposited Successfully","Confirmation",JOptionPane.INFORMATION_MESSAGE);
				else
				JOptionPane.showMessageDialog(f,"Deposition Unsuccessful","Confirmation",JOptionPane.INFORMATION_MESSAGE);
			}
			if(ch == -1)
			{
				int x = BankGlobal.accounts.get(BankGlobal.in).withdraw(b);
				if(x == 1)
				JOptionPane.showMessageDialog(f,"Withdrawn Successfully","Confirmation",JOptionPane.INFORMATION_MESSAGE);
				else if(x == 0)
				JOptionPane.showMessageDialog(f,"Insufficient Balance","Confirmation",JOptionPane.INFORMATION_MESSAGE);
				else
				JOptionPane.showMessageDialog(f,"Withdrawal Unsuccessful","Confirmation",JOptionPane.INFORMATION_MESSAGE);

			}
			if(ch == -2)
			{
				int x = BankGlobal.accounts.get(BankGlobal.in).fund(Long.parseLong(tfa.getText()),b);
				if(x > 0)
				JOptionPane.showMessageDialog(f,"Fund Transfer Successful","Confirmation",JOptionPane.INFORMATION_MESSAGE);
				else if(x == 0)
				JOptionPane.showMessageDialog(f,"Fund Transfer Failed: Due to Insufficient Balance","Confirmation",JOptionPane.INFORMATION_MESSAGE);
				else
				JOptionPane.showMessageDialog(f,"Beneficiary Account doesnot exist","Error",JOptionPane.ERROR_MESSAGE);
			}
			tfa.setText("");
			tfb.setText("");
		}
		else if(ae.getActionCommand().equals("Back"))
		{
			f.dispose();
		}
	}
	void dwp()
	{
		p2 = new JPanel(null);
		lc = new JLabel("Enter the Amount");
		lc.setBounds(125,175,100,30);
		tfb = new JTextField();
		tfb.setBounds(275,175,100,30);
		bs = new JButton("Submit");
		bs.setBounds(200,230,100,30);
		bs.addActionListener(this);
		p2.add(lc);
		p2.add(tfb);
		p2.add(bs);
		p2.add(bb);
		cp.add(p2,"p2");
	}
	void ftp()
	{
		p3 = new JPanel(null);
		lc = new JLabel("Enter the Beneficiary Account");
		lc.setBounds(115,175,200,30);
		JLabel lf = new JLabel("Enter the Amount");
		lf.setBounds(115,225,200,30);
		tfa = new JTextField();
		tfb = new JTextField();
		tfa.setBounds(330,175,100,30);
		tfb.setBounds(330,225,100,30);
		bs = new JButton("Submit");
		bs.setBounds(200,250,100,30);
		bs.addActionListener(this);
		p3.add(lc);
		p3.add(lf);
		p3.add(tfa);
		p3.add(tfb);
		p3.add(bs);
		p3.add(bb);
		cp.add(p3,"p3");
	}
}

class ATM implements ActionListener
{
	JFrame f;
	JLabel lt,la,lp;
	JTextField tfa,tfp;
	JButton bb,bs;
	ATM()
	{
		f = new JFrame("ATM");
		f.setSize(500,500);
		f.setLayout(null);
		Font ft = new Font("Times New Roman",Font.BOLD,30);
		lt = new JLabel("ATM");
		lt.setBounds(215,20,70,30);
		lt.setFont(ft);
		la = new JLabel("Account Number");
		la.setBounds(100,90,100,30);
		lp = new JLabel("Pin");
		lp.setBounds(100,130,100,30);
		tfa = new JTextField();
		tfa.setBounds(250,90,110,30);
		tfp = new JTextField();
		tfp.setBounds(250,130,110,30);
		bb = new JButton("Back");
		bb.setBounds(10,20,70,30);
		bb.addActionListener(this);
		bs = new JButton("Submit");
		bs.setBounds(200,200,100,30);
		bs.addActionListener(this);
		f.add(lt);
		f.add(la);
		f.add(lp);
		f.add(tfa);
		f.add(tfp);
		f.add(bb);
		f.add(bs);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("Submit"))
		{
			long a = Long.parseLong(tfa.getText());
			int p = Integer.parseInt(tfp.getText());
			if(BankGlobal.search(a,p) >= 0)
			{
				ATMInterface atmi = new ATMInterface();
			}
			else
			{
				JOptionPane.showMessageDialog(f,"Invalid Input","Error",JOptionPane.ERROR_MESSAGE);
			}
			tfa.setText("");
			tfp.setText("");
			
		}
		else if(ae.getActionCommand().equals("Back"))
		{
			f.dispose();
		}
	}
}

class Registration implements ActionListener
{
	JFrame f;
	JButton bb,bs;
	JLabel lt,la,lp,lb;
	JTextField tfa,tfp,tfb;
	Registration()
	{
		f = new JFrame("Registration");
		f.setSize(500,500);
		f.setLayout(null);
		Font ft = new Font("Times New Roman", Font.BOLD,30);
		lt = new JLabel("Registration");
		lt.setFont(ft);
		lt.setBounds(165,20,170,35);
		lp = new JLabel("Enter the Account Number");
		lp.setBounds(70,150,190,30);
		la = new JLabel("Enter the correct PIN");
		la.setBounds(70,200,190,30);
		lb = new JLabel("Enter the Balance Available");
		lb.setBounds(70,250,190,30);
		tfa = new JTextField();
		tfa.setBounds(280,150,125,30);
		tfp = new JTextField();
		tfp.setBounds(280,200,125,30);
		tfb = new JTextField();
		tfb.setBounds(280,250,125,30);
		bb = new JButton("Back");
		bb.setBounds(10,20,70,30);
		bb.addActionListener(this);
		bs = new JButton("Submit");
		bs.setBounds(200,310,100,30);
		bs.addActionListener(this);
		f.add(lt);
		f.add(la);
		f.add(lp);
		f.add(lb);
		f.add(tfa);
		f.add(tfp);
		f.add(tfb);
		f.add(bb);
		f.add(bs);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("Submit"))
		{
			long a = Long.parseLong(tfa.getText());
			int p = Integer.parseInt(tfp.getText());
			float b = Float.parseFloat(tfb.getText());
			if(BankGlobal.search(a,p) == -2)
			{
				BankGlobal.accounts.add(new Bank(a,p,b));
				JOptionPane.showMessageDialog(f,"Registered Successfully","Confirmation",JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				JOptionPane.showMessageDialog(f,"Account Already Exist","Confirmation",JOptionPane.INFORMATION_MESSAGE);
			}
			tfa.setText("");
			tfp.setText("");
			tfb.setText("");
		}
		else if(ae.getActionCommand().equals("Back"))
		{
			f.dispose();
		}
	}
}

class Home implements ActionListener
{
	JFrame f;
	JButton breg,blog;
	JLabel lt;
	Home()
	{
		f = new JFrame("ATM");
		f.setSize(500,500);
		f.setLayout(null);
		Font ft = new Font("Times New Roman", Font.BOLD,30);
		lt = new JLabel("ATM");
		lt.setFont(ft);
		lt.setBounds(215,20,70,35);
		breg = new JButton("Register Your Account");
		breg.setBounds(150,150,200,30);
		breg.addActionListener(this);
		blog = new JButton("Login into Your Account");
		blog.setBounds(150,200,200,30);
		blog.addActionListener(this);
		f.add(lt);
		f.add(breg);
		f.add(blog);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("Register Your Account"))
		{
			Registration reg = new Registration();	
		}
		else if(ae.getActionCommand().equals("Login into Your Account"))
		{
			ATM atm = new ATM();
		}
	}
}

class ATMMain
{
	public static void main(String ar[])
	{
		Home homepage = new Home();
	}
}