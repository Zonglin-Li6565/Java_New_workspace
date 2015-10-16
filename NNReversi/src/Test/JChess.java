package Test;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;

//import Reversi.Chessspot;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
class Chessspot{
	int x;
	int y;
	double rate;
	int color;
	public Chessspot(int x1,int y1,int colo){
		rate=0;
		x=x1;
		y=y1;
		color=colo;
	}
	public int[] getLoc(){
		int[] l=new int[2];
		l[0]=x;
		l[1]=y;
		return l;
	}
	public double getRate(){
		return rate;
	}
	public void setRate(double r){
		rate=r;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
}
class AI{
	public static final int ADVERSE=0;
	public static final int SELF=1;
	public static final int POINTS=2;
	public static int c1=2;
	public static int steps=10;
	public static int invert;
	public static int[] test(int [][] chessspot){
		ArrayList<Chessspot> chess=new ArrayList<Chessspot>();
		for(int i=0;i< 8;i++){
			for(int j=0; j<8;j++){
				if(chessspot[i][j]==4){
					chess.add(new Chessspot(i,j,4));
					System.out.println("add chessspot"+"("+ i+" ,"+j+")");
				}
			}
		}
		int randompointer=(int)(Math.random()*chess.size());
		int k[]=new int[2];
		k[0]=chess.get(randompointer).x;
		k[1]=chess.get(randompointer).y;
		System.out.println("in AI, x= "+k[0]+" y= "+ k[1]);
		return k;
	}
	public static int[] driver(int[][] chessspot){
		int[][] record= new int[3][steps];
		int[][] chessnew=new int[8][8];
		int[] t=new int[2];
		for(int i=0;i<8;i++){
			for(int p=0;p<8;p++){
				chessnew[i][p]=chessspot[i][p];
			}
		}
		ArrayList<Chessspot> chess=new ArrayList<Chessspot>();
		for(int i=0;i<8;i++){
			for(int p=0;p<8;p++){
				if(chessspot[i][p]==4){
					chess.add(new Chessspot(i,p,4));
				}
			}
		}
		for(Chessspot ch:chess){
			chessnew[ch.getX()][ch.getY()]=c1;                      //drop the chess
        	ChessAction.pocessChess(ch.getX(), ch.getX(), c1, true, chessnew);
        	for(int q=0;q<8;q++){
				if(chessnew[ch.getX()][ch.getY()]==4){
					chessnew[ch.getX()][ch.getY()]=0;
				}
			}
			
        	ChessAction.createHint(c1, chessnew);
        	//for(int i=0;i<8;i++){
        	//	for(int k=0;k<8;k++){
        	//		System.out.print(chessnew[k][i]+" ");
        	//	}
        	//	System.out.print("\n");
        	//}
     		record=selectChess(chessnew,c1,steps,1,record);
     		for(int i=0;i<3;i++){
     			for(int p=0; p<steps;p++){
     				System.out.print(record[i][p]);
     			}
     			System.out.print("\n");
     		}
    		ch.setRate(Rate(record));
    		System.out.println("Rate:"+Rate(record));
		}
		double rate=-100;
		int [] a =new int [2];
		for(Chessspot che:chess){
			if (che.getRate()>rate){
				rate=che.getRate();
				System.out.println("rate= "+rate);
				a=che.getLoc();
				System.out.println("a[0]="+a[0]+"a[1]="+a[1]);
			}
		}
		
		return a;
	}
	public static int[][] selectChess(int[][] chesspot, int c, int requiredsteps, int steps, int[][]records){
		System.out.println("Step"+steps);
		if(steps>requiredsteps){
			return records;
		}
		int[][] chessnew2=new int[8][8];
		for(int i=0;i<8;i++){
			for(int p=0;p<8;p++){
		        if(chesspot[i][p]==4){
		        	chessnew2[i][p]=c;
		        	for(int k=0;k<8;k++){
						for(int q=0;q<8;q++){
							if(chessnew2[k][q]==4){
								chessnew2[k][q]=0;
							}
						}
					}
		        	ChessAction.pocessChess(i, p, c, true, chessnew2);
		        	ChessAction.createHint((c+1)%2, chessnew2);
		        	if(isWin(chessnew2,c)){ 
		        		if(whoWins(chessnew2)==c1){
		        			records[ADVERSE][steps-1]=0;
		        			records[SELF][steps-1]=64;
		        			records[POINTS][steps-1]=64;
		        			return records;
		        		}else{
		        			records[ADVERSE][steps-1]=64;
		        			records[SELF][steps-1]=0;
		        			records[POINTS][steps-1]=-64;
		        			return records;
		        		}
		        	}
		            if(c==c1){
		            	System.out.println("entered1");
		            	int perilous1=0;
		            	int perilous2=0;
		            	for(int a=0;a<8;a++){
		            		for(int b=0;b<8;b++){
		            			if(chessnew2[a][b]==4){
		            				records[SELF][steps-1]++;
		            				System.out.println("self+1");
		            			}
		            			if(chessnew2[a][b]==4&&((a==1&&b==0)||(a==0&&b==1)||
		            					(a==6&&b==0)||(a==0&&b==6)||(a==1&&b==7)||(a==7&&b==1)||
		            					(a==7&&b==6)||(a==6&&b==7))){
		            				perilous2++;
		            			}
		            			if(chessnew2[a][b]==4&&((a==1&&b==1)||(a==6&&b==1)||
		            					(a==6&&b==1)||(a==6&&b==6))){
		            				perilous1++;
		            			}
		            		}
		            	}
		            	if(records[SELF][steps-1]==0){
		            		int [][] temp=new int[3][requiredsteps];   
				            temp=selectChess(chessnew2,(c+1)%2, requiredsteps,steps+1, records);
				            for(int t=0;t<3;t++){
				            	for(int d=0;d<7;d++){
				            		records[t][d]+=temp[t][d];
				            	}
				            }
				            temp=null;
		            	}else{
		            		
		            	if(chessnew2[0][0]==4||chessnew2[7][7]==4||
		 		               chessnew2[0][7]==4||chessnew2[7][0]==4){
		            		records[POINTS][steps-1]+=5;
		 		            }
		            	records[POINTS][steps-1]-=perilous1/records[SELF][steps-1]*8;
		            	records[POINTS][steps-1]-=perilous2/records[SELF][steps-1]*5;
		            	}
		            }else{
		            	System.out.println("entered2");
		            	int perilous1=0;
		            	int perilous2=0;
		            	for(int a=0;a<8;a++){
		            		for(int b=0;b<8;b++){
		            			if(chessnew2[a][b]==4){
		            				records[ADVERSE][steps-1]++;
		            				System.out.println("adv+1");
		            			}
		            			if(chessnew2[a][b]==4&&((a==1&&b==0)||(a==0&&b==1)||
		            					(a==6&&b==0)||(a==0&&b==6)||(a==1&&b==7)||(a==7&&b==1)||
		            					(a==7&&b==6)||(a==6&&b==7))){
		            				perilous2++;
		            			}
		            			if(chessnew2[a][b]==4&&((a==1&&b==1)||(a==6&&b==1)||
		            					(a==6&&b==1)||(a==6&&b==6))){
		            				perilous1++;
		            			}
		            		}
		            	}
		            	if(records[ADVERSE][steps-1]==0){
		            		int [][] temp=new int[3][requiredsteps];   
				            temp=selectChess(chessnew2,(c+1)%2, requiredsteps,steps+1, records);
				            for(int t=0;t<3;t++){
				            	for(int d=0;d<7;d++){
				            		records[t][d]+=temp[t][d];
				            	}
				            }
				            temp=null;
		            	}else{
		            	if(chessnew2[0][0]==4||chessnew2[7][7]==4||
			 		               chessnew2[0][7]==4||chessnew2[7][0]==4){
			            		records[POINTS][steps-1]-=5;
			 		            }
			            	records[POINTS][steps-1]+=perilous1/records[SELF][steps-1]*8;
			            	records[POINTS][steps-1]+=perilous2/records[SELF][steps-1]*5;
		            	}
		            }
		        	int [][] temp=new int[3][requiredsteps];   
		            temp=selectChess(chessnew2,(c+1)%2, requiredsteps,steps+1, records);
		            for(int t=0;t<3;t++){
		            	for(int d=0;d<7;d++){
		            		records[t][d]+=temp[t][d];
		            	}
		            }
		            temp=null;
		        }
			}
		}
		return records;
	}
	public static double Rate(int [][] records){
		double rate=0;
		int [] self=new int[steps/2];
		int [] adv=new int[steps/2];
		int point=0;
		for(int i=0;i<steps;i++){
			if(i%2==0){
				self[i/2]=records[SELF][i];
			}else{
				self[i/2]=records[ADVERSE][i];
			}
			point+=records[POINTS][i];
		}
		inversionFinder(self);
		int inverts=invert;
		invert=0;
		inversionFinder(adv);
		int inverta=invert;
		invert=0;
		return (double)inverta*point/inverts;
	}
	public static boolean isWin(int[][] chessspot,int count){
		int [][] chessspot1=new int[8][8];
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				chessspot1[i][j]=chessspot[i][j];
			}
		}
		int count1=0;
		int count2=0;
		int count4=0;
		for(int[] i:chessspot){
			for(int p:i){
				if(p==1){
					count1++;
				}
				if(p==2){
					count2++;
				}
				if(p==4){
					count4++;
				}
			}
		}
		if(count4==0&&(count1+count2)>=63){
			count++;
			boolean b=ChessAction.createHint(count%2+1,chessspot1);
			if(b){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
		
	}
	public static int whoWins(int[][]chessspot){
		int count1=0;
		int count2=0;
		for(int[] i:chessspot){
			for(int p:i){
				if(p==1){
					count1++;
				}
				if(p==2){
					count2++;
				}

			}
		}
		if(count1>=count2){
			return 1;
		}else{
			return 2;
		}
	}
	public static int[] inversionFinder(int [] array){
		if(array.length==1){
			return array;
		}
		int A[]=new int[array.length/2];
		int B[]=new int[array.length-array.length/2];
		int d=0;
		for(;d<A.length;d++){
			A[d]=array[d];
		}
		for(;d<array.length;d++){
			B[d-A.length]=array[d];
		}
		int C[]=inversionFinder(A);
		int D[]=inversionFinder(B);
		A=null;
		B=null;
		A=new int[C.length+1];                    //ahead
		B=new int[D.length+1];                    //after
		for(int i=0;i<C.length;i++){
			A[i]=C[i];
		}
		for(int i=0;i<D.length;i++){
			B[i]=D[i];
		}
		A[A.length-1]=B[B.length-1]=Integer.MAX_VALUE;
		C=new int[array.length];
		D=null;
		int j=0;
		int k=0;
		for(int i=0;i<C.length;i++){             //A and B
			if(A[j]<=B[k]){
				C[i]=A[j];
				j++;
			}else{
				C[i]=B[k];
				invert+=A.length-j-1;
				k++;
			}
		}
		return C;
	}
}

class JDrawPanel extends JPanel{
	 public int x1=JChess.l;
	 public int[][] x2;
	 @Override
	public void paint(Graphics g){
		x2=JChess.getChessspot();
 		super.paint(g);
 		//setForeground(Color.black);
 		//System.out.println("painted");
 		//System.out.println(x2+" painted");
 		for(int j=1;j<8;j++){
 			g.drawLine(0, x1*j, x1*8, x1*j); // draw x line
 			g.drawLine(x1*j, 0, x1*j, 8*x1); // draw y line
 		}
 			for(int k=0;k<8;k++){
 				for(int p=0;p<8;p++){
 					if(x2[p][k]==2){
 						g.setColor(Color.black);
 						g.fillOval(p*x1, k*x1, x1,x1);// paint black chess(2)
 					}
 					if(x2[p][k]==1){
 						g.setColor(Color.white);
 						g.fillOval(p*x1, k*x1, x1,x1); // paint white chess(1)
 					}
 					if(x2[p][k]==4){	 					                         
 						g.setColor(Color.black); //draw the hint notation
 						g.drawOval(p*x1, k*x1, x1, x1);
 						setForeground(Color.black);	
 				}
 			}
 		}
 	 }
} 

class ChessAction{
	/*  check is filled and return the chess location on the panel	
	 *  returns [x,y]
	 */
	public static int[] processAction(int x, int y){     
		  int x1=x/JChess.l;
          int y1=y/JChess.l;
	      //if(JChess. chessspot[x1][y1]!=1&&JChess. chessspot[x1][y1]!=2){
          if(JChess. chessspot[x1][y1]==4){
	    	  int[] i={x1,y1};
	    	  return i;
	      }else{
	    	  return null;
	      }
	}
	/*creates the circle represents the possible location to one color
	 * 
	 */
	public static boolean createHint(int c,int [][]chessspot){
		boolean b=false;
		  for(int i=0;i<8;i++){
			 for(int p=0;p<8;p++){
				 if( chessspot[i][p]!=1&& chessspot[i][p]!=2){
				 if(pocessChess(i,p,c,false,chessspot)){
					 chessspot[i][p]=4;
					 b=true;
					 //System.out.println("( "+i+","+p+" )");
				 }
				 }
			 }
		  }
		  return b;
	}
	/*
	 * to change reverse the chess and return 
	 */
	public static boolean pocessChess(int x, int y, int c,boolean changeColor1,int [][] chessspot){
		int[] s1=new int[8];
		boolean changeColor=changeColor1;
		int x1=x;
		int y1=y;
		int c1=c;
        while(x<7){ //x+ direction
        	if(chessspot[x+1][y]!=c&&chessspot[x+1][y]!=0&&chessspot[x+1][y]!=4){
        	s1[0]++;
        	x++;
        	}else{
        		break;
        	}
        }
        if(x<7&&chessspot[x+1][y]==0||x==7||chessspot[x+1][y]==4){
        	s1[0]=0;
        }
        if(changeColor)
        changeColor(x1,y1,s1[0],1,c1,chessspot);      
        x=x1;
        while(x<7&&y<7){ //incline +direction
        	if(chessspot[x+1][y+1]!=c&&chessspot[x+1][y+1]!=0&&chessspot[x+1][y+1]!=4){
        	s1[1]++;
        	x++;
        	y++;
        }else{     
        	break;
        }
        }	
        if(x<7&&y<7&&chessspot[x+1][y+1]==0||(x==7||y==7)||chessspot[x+1][y+1]==4){
        	s1[1]=0;
        }
        if(changeColor)
        changeColor(x1,y1,s1[1],2,c1,chessspot);      
        x=x1;
        y=y1;
        while(y<7){ // y+ direction
        	if(chessspot[x][y+1]!=c&&chessspot[x][y+1]!=0&&chessspot[x][y+1]!=4){
        	s1[2]++;
        	y++;
        	}else{
        		break;
        }
        }
        	if(y<7&&chessspot[x][y+1]==0||y==7||chessspot[x][y+1]==4){
        		s1[2]=0;
        	}
        	if(changeColor)
        changeColor(x1,y1,s1[2],4,c1,chessspot);
        y=y1;
        while(y>0&&x<7){ // -decline direction
        	if(chessspot[x+1][y-1]!=c&&chessspot[x+1][y-1]!=0&&chessspot[x+1][y-1]!=4){
        	s1[3]++;
        	x++;
        	y--;
        	}else{
        		break;
        }
        }
        	if(y>0&&x<7&&chessspot[x+1][y-1]==0||(y==0||x==7)||chessspot[x+1][y-1]==4){
        		s1[3]=0;
        	}
        	//System.out.println("s1[3]="+s1[3]);
        	if(changeColor)
        changeColor(x1,y1,s1[3],3,c1,chessspot);
        x=x1;
        y=y1;
        while(x>0){ //-x direction
        	if(chessspot[x-1][y]!=c&&chessspot[x-1][y]!=0&&chessspot[x-1][y]!=4){
        	s1[4]--;
        	x--;
        	}else{
        		break;
        }
        }
        	if(x>0&&chessspot[x-1][y]==0||(x==0)||chessspot[x-1][y]==4){
        		s1[4]=0;
        	}
        	if(changeColor)
        changeColor(x1,y1,s1[4],1,c1,chessspot);
        x=x1;
        while((x>0&&y>0)){ //incline -direction
        	if(chessspot[x-1][y-1]!=c&&chessspot[x-1][y-1]!=0&&chessspot[x-1][y-1]!=4){
        	s1[5]--;
        	x--;
        	y--;
        	}else{
        		break;
        }
        }
        	if(x>0&&y>0&&chessspot[x-1][y-1]==0||(x==0||y==0)||chessspot[x-1][y-1]==4){
        		s1[5]=0;
        	}
        	if(changeColor)
        changeColor(x1,y1,s1[5],2,c1,chessspot);
        x=x1;
        y=y1;
        while(y>0){ // y- direction
        	if(chessspot[x][y-1]!=c&&chessspot[x][y-1]!=0&&chessspot[x][y-1]!=4){
        	s1[6]--;
        	y--;
        	}else{
        		break;
        	}
        }
        if(y>0&&chessspot[x][y-1]==0||y==0||chessspot[x][y-1]==4){
        	s1[6]=0;
        }
        if(changeColor)
        changeColor(x1,y1,s1[6],4,c1,chessspot);
        y=y1;
        x=x1;
        while(x>0&&y<7){ // -decline direction
        	if(chessspot[x-1][y+1]!=c&&chessspot[x-1][y+1]!=0&&chessspot[x-1][y+1]!=4){
        	s1[7]--;
        	y++;
        	x--;
        	}else{
        		 //System.out.println("(1)s1[7]="+s1[7]);
        		break;
        }
        }
        	if(x>0&&y<7&&chessspot[x-1][y+1]==0||(x==0||y==7)||chessspot[x-1][y+1]==4){
        		s1[7]=0;
        		//System.out.println("turn to 0");
        	}
        	//System.out.println(s1[7]);
        	if(changeColor)
        changeColor(x1,y1,s1[7],3,c1,chessspot);
        //System.out.println("(2)s1[7]="+s1[7]);
        int t=0;
        //System.out.print("{");
        for(int p:s1){
        	//System.out.print(p+" ");
        	if(p==0){
        		t++;	
        	}
        }
        //System.out.println("}");
        //System.out.println("t="+t);
        if(t==8){
        	chessspot[x1][y1]=0;
        	return false;
        }else{
        	return true;
        }
        }
	/*
	 * usually used in pocessChess(...) and help to reverse the chess.
	 */
	public static void changeColor(int x1, int y1, int step, int type, int c,int[][] chespot){
		if(step>0){
		for(int i=1;i<=step;i++){
			if(type==1){   //x direction
				chespot[x1+i][y1]=c;
				//continue;
				//System.out.println("("+x1+","+y1+")");
				//System.out.println("("+type+")"+"step="+step);
			}
			if(type==2){ //+ incline direction
				chespot[x1+i][y1+i]=c;
				//continue;
				//System.out.println("("+x1+","+y1+")");
				//System.out.println("("+type+")"+"step="+step);
			}
			if(type==3){// - incline direction
				chespot[x1+i][y1-i]=c;
				//continue;
				//System.out.println("("+x1+","+y1+")");
				//System.out.println("("+type+")"+"step="+step);
			}
			if(type==4){ //y direction
				chespot[x1][y1+i]=c;
				//continue;
				//System.out.println("("+x1+","+y1+")");
				//System.out.println("("+type+")"+"step="+step);
			}
		}
		}
		if(step<0){
			for(int i=0;i>=step;i--){
				if(type==1){   //x direction
					chespot[x1+i][y1]=c;
					//continue;
					//System.out.println("("+x1+","+y1+")");
					//System.out.println("("+type+")"+"step="+step);
				}
				if(type==2){ //+ incline direction
					chespot[x1+i][y1+i]=c;
					//continue;
					//System.out.println("("+x1+","+y1+")");
					//System.out.println("("+type+")"+"step="+step);
				}
				if(type==3){// - incline direction
					chespot[x1+i][y1-i]=c;
					//continue;
					//System.out.println("("+x1+","+y1+")");
					//System.out.println("("+type+")"+"step="+step);
				}
				if(type==4){ //y direction
					chespot[x1][y1+i]=c;
					//continue;
					//System.out.println("("+x1+","+y1+")");
					//System.out.println("("+type+")"+"step="+step);
				}
			}
		}
}
}
class Winnote extends JDialog {

	private final JPanel contentPanel = new JPanel();

	
	public Winnote(String s) {
		setBounds(100, 100, 449, 170);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(154, 84, 113, 27);
		contentPanel.add(btnOk);
		btnOk.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				setVisible(false);
			}
			
		});
		
		JLabel lblNewLabel = new JLabel("s");
		lblNewLabel.setIcon(new ImageIcon(Winnote.class.getResource("/javax/swing/plaf/metal/icons/Inform.gif")));
		lblNewLabel.setBounds(55, 35, 35, 37);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(s);
		lblNewLabel_1.setBounds(130, 44, 72, 18);
		contentPanel.add(lblNewLabel_1);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("OK");
				okButton.setBounds(377, 5, 49, 27);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}


public class JChess extends JFrame {
	private static JTextField textField;  //show the number of chess of black
	private static JTextField textField_1; //show the number of chess of white
	private JPanel contentPane;
	public static  int l=50;// the length of the border of each cell
	private static JDrawPanel Jp1; // the JDrawPanel
	public static int[][] chessspot;
	public static int count; // decide the color of chess 
	public static int Bnumber;  //count the number of black chess
	public static int Wnumber; // count the number of white chess
	public static boolean isWin=false;
    public static JButton jb2;
    private static JTextArea playArea;
    private static int key=0;
    private static int[][] forPlay;
    private static boolean play=false;
    private static int playIndex;
    
	/**
	 * Launch the application.
	 */
    public static int[][] getChessspot(){
    	return chessspot;
    }
    public static int[][] processRecord(String s){
    	int [][] aa=new int[s.length()/12][3];
    	for(int i=0;i<s.length()/12;i++){
    		if(s.substring(i*12,i*12+1).equals("w")){
    			aa[i][2]=1;
    		}else{
    			aa[i][2]=2;
    		}
    		//aa[i][0]=Integer.parseInt(s.substring(i*12+6,i*12+7));
    		aa[i][0]=Integer.parseInt(s.substring(i*12+8,i*12+9));
    	}
    	return aa;
    }
	public static int countChess(){
		int i=0;
		for(int p[]:chessspot){
			for(int t:p){
			if(t==1||t==2){
				i++;
			}
			}
		}
		return i;
	}
	public static void main(String[] args) {
		new JChess().setVisible(true);
		//System.out.println("x");
		for(int i=0;i<args.length;i++){
			System.out.print(args[i]);
		}
}
    public static void initialize(){
    	chessspot=new int[8][8];
		chessspot[3][3]=chessspot[4][4]=1;
		chessspot[3][4]=chessspot[4][3]=2;
		Jp1.repaint();
    }
    public static void processMouseAction(MouseEvent e){
    	if(!play){
			Wnumber=0;
			Bnumber=0;
			for(int i=0;i<8;i++){
				for(int t=0;t<8;t++){
					if(chessspot[i][t]==4){
						chessspot[i][t]=0;
					}
				}
			}
			int p1=countChess();
			if(ChessAction.processAction(e.getX(), e.getY())!=null){
				int[] p=ChessAction.processAction(e.getX(), e.getY());
				int x=p[0];
				int y=p[1];
				chessspot[x][y]=count%2+1;
			    boolean z=ChessAction.pocessChess(x, y, count%2+1,true,chessspot);
			    count ++;
			    int t=countChess();
			    if(key!=t){
					if(count%2+1==2){
						playArea.append("white"+"("+x+","+y+")"+"\n");
						key=countChess();
					}else{
						playArea.append("black"+"("+x+","+y+")"+"\n");
						key=countChess();
					}
					}
			    
			    if(p1==t){
			    	count--;
			    }
			    ChessAction.createHint(count%2+1,chessspot);
			    //count++;
			    for(int x1=0;x1<8;x1++){
			    	for(int y1=0;y1<8;y1++){
			    		System.out.print(chessspot[y1][x1]);
			    	}
			    	System.out.print("\n");
			    }
			    if(!false&&z){
			    int[] d=new int[2];
			    d=AI.test(chessspot);
			    System.out.println("d:"+d[0]+" "+d[1]);
				chessspot[d[0]][d[1]]=count%2+1;
				
			    ChessAction.pocessChess(d[0], d[1], count%2+1,true,chessspot);
			    System.out.println("*********************");
				for(int x1=0;x1<8;x1++){
			    	for(int y1=0;y1<8;y1++){
			    		System.out.print(chessspot[y1][x1]);
			    	}
			    	System.out.print("\n");
			    }
			    count ++;
			    t=countChess();
			    if(key==t){
			    	System.out.println("************performed");
					if(count%2+1==2){
						
						playArea.append("white"+"("+d[0]+","+d[1]+")"+"\n");
						key=countChess();
					}else{
						System.out.println("*********performed");
						playArea.append("black"+"("+d[0]+","+d[1]+")"+"\n");
						key=countChess();
					}
					}
			    
			    if(p1==t){
			    	count--;
			    }
			    ChessAction.createHint(count%2+1,chessspot);
			    }
			    if(key!=t){
					if(count%2+1==2){
						playArea.append("white"+"("+x+","+y+")"+"\n");
						key=countChess();
					}else{
						playArea.append("black"+"("+x+","+y+")"+"\n");
						key=countChess();
					}
					}
			    Jp1.repaint();
			}
	        for(int i[]: chessspot){
	        	for(int t: i){
	        		if(t==1){
	        			Wnumber++;
	        		}
	        		if(t==2){
	        			Bnumber++;
	        		}
	        	}
	        }
	        textField.setText(new Integer(Bnumber).toString());
	        textField_1.setText(new Integer(Wnumber).toString());
	        if(AI.isWin(chessspot,count)){
	        	isWin=AI.isWin(chessspot,count);
	        	if(Wnumber<Bnumber){
	        		new Winnote("Black wins!").setVisible(true);
	        	}
	        	if(Wnumber>Bnumber){
	        		new Winnote("White wins!").setVisible(true);
	        	}
	        	if(Wnumber==Bnumber){
	        		new Winnote("Draw level!").setVisible(true);
	        	}
	        }
		}
    }
	/**
	 * Create the frame.
	 * @return 
	 */
	public JChess() {
		//initialize();
		chessspot=new int[8][8];
		chessspot[3][3]=chessspot[4][4]=2;
		chessspot[3][4]=chessspot[4][3]=1;
		chessspot[3][2]=chessspot[2][3]=chessspot[5][4]=chessspot[4][5]=4;
		setTitle("Reversi Chess");
		setIconImage(Toolkit.getDefaultToolkit().getImage(JChess.class.getResource("/com/sun/java/swing/plaf/motif/icons/DesktopIcon.gif")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(250, 70, 750, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//ImageIcon im1=new ImageIcon("C:\\Users\\Administrator\\workspace\\ChessBoard\\src\\Chessnew\\back ground.jpg");
		//JLabel Jl1=new JLabel(im1); 
		//contentPane.add(Jl1);
		//Jl1.setBounds(0, 0, 750, 650);
		
		final JButton btnNewButton = new JButton("Restart");
		btnNewButton.setBounds(510, 107, 163, 27);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
			for(int i=0;i<8;i++){
				for(int t=0;t<8;t++){
					chessspot[i][t]=0;
			}
			}
			//chessspot=new int [8][8];
			System.out.println(chessspot);
			//System.out.println(getChessspot());
			chessspot[3][3]=chessspot[4][4]=2;
			chessspot[3][4]=chessspot[4][3]=1;
			chessspot[3][2]=chessspot[2][3]=chessspot[5][4]=chessspot[4][5]=4;
			textField.setText("2");
			textField_1.setText("2");
			playArea.setText("");
			Jp1.repaint();
		//	Jb5.setEnabled(!true);
		//	Jb6.setEnabled(!true);
		//	Jp1.setEnabled(!false);
		//	btnNewButton1.setEnabled(!false);
		//	jb2.setEnabled(!false);
		//	Jb3.setEnabled(!false);
			}			
		});
		final JButton btnNewButton1 = new JButton("next");
		btnNewButton1.setBounds(510, 167,163, 27);
		contentPane.add(btnNewButton1);
		btnNewButton1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				boolean b=true;
				for(int [] i:chessspot){
					for(int p:i){
						if(p==4)
							b=false;
					}
				}
				if(b){
				count++;
				ChessAction.createHint(count%2+1,chessspot);
				Jp1.repaint();
				}
			}
			
		});
		jb2=new JButton("save");
		jb2.setBounds(510, 227,163, 27);
		contentPane.add(jb2);
		jb2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				File f=new File("D:\\","Reversi.che");
				
					if(f.exists()){
						f.delete();
					}
					try{
					FileOutputStream out=new FileOutputStream(f);
					ObjectOutputStream dot=new ObjectOutputStream(out);
					dot.writeObject(chessspot);
					System.out.println("Write successfully to "+"D:\\Reversi.che");
					dot.close();
					}catch (IOException e1) {
						// TODO �Զ����ɵ� catch ��
						throw new IllegalArgumentException("no file found !!!!!");
					}
					File f1=new File("D:\\","Reversinote.che");
					
					if(f1.exists()){
						f1.delete();
					}
					try{
					FileOutputStream out=new FileOutputStream(f1);
					ObjectOutputStream dot=new ObjectOutputStream(out);
					dot.writeObject(playArea.getText());
					System.out.println("Write successfully to "+"D:\\Reversinote.che");
					dot.close();
					}catch (IOException e1) {
						// TODO �Զ����ɵ� catch ��
						throw new IllegalArgumentException("no file found !!!!!");
					}
					
			}
			
		});
		
		final JButton Jb3=new JButton("open");
		Jb3.setBounds(510, 287,163, 27);
		contentPane.add(Jb3);
		Jb3.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				File f=new File("D:\\","Reversi.che");
				
					if(f.exists()){
					try{
					FileInputStream in=new FileInputStream("D:\\Reversi.che");
					ObjectInputStream doi=new ObjectInputStream(in);
					chessspot=(int[][]) doi.readObject();
					Jp1.repaint();
					System.out.println("open successfully");
					doi.close();
					}catch (IOException | ClassNotFoundException e1) {
						// TODO �Զ����ɵ� catch ��
						throw new IllegalArgumentException("no file found !!!!!");
					}
					}else{
						new Winnote("NO FILE FOUND!").setVisible(true);
					}
					int w=0;
					int b=0;
	    	        for(int i[]: chessspot){
	    	        	for(int t: i){
	    	        		if(t==1){
	    	        			w++;
	    	        		}
	    	        		if(t==2){
	    	        			b++;
	    	        		}
	    	        	}
	    	        }
	    	        textField.setText(new Integer(b).toString());
	    	        textField_1.setText(new Integer(w).toString());
			File f1=new File("D:\\","Reversinote.che");
			
			if(f1.exists()){
			try{
			FileInputStream in=new FileInputStream("D:\\Reversinote.che");
			ObjectInputStream doi=new ObjectInputStream(in);
			System.out.println("open successfully");
			String i=(String) doi.readObject();
			playArea.setText(i);
			doi.close();
			}catch (IOException | ClassNotFoundException e1) {
				// TODO �Զ����ɵ� catch ��
				throw new IllegalArgumentException("no file found !!!!!");
			}
			}else{
				new Winnote("NO FILE FOUND!").setVisible(true);
			}
			}
		});
		
		final JButton Jb5=new JButton("<");
		Jb5.setBounds(50, 550, 50, 50);
		contentPane.add(Jb5);
		Jb5.setEnabled(false);
		
		final JButton Jb6=new JButton(">");
		Jb6.setBounds(100, 550, 50, 50);
		contentPane.add(Jb6);
		Jb6.setEnabled(false);
		Jb6.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO �Զ����ɵķ������
				System.out.println("x="+forPlay[0][playIndex]+"y="+forPlay[1][playIndex]+"c="+forPlay[2][playIndex]);
				chessspot[forPlay[0][playIndex]][forPlay[1][playIndex]]=forPlay[2][playIndex];
				ChessAction.pocessChess(forPlay[0][playIndex],forPlay[1][playIndex],forPlay[2][playIndex],true,chessspot);
				playIndex++;
			}
			
		});
		
		final JButton Jb4=new JButton("play");
		Jb4.setBounds(510, 347,163, 27);
		contentPane.add(Jb4);
		Jb4.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO �Զ����ɵķ������
				play=!play;
				if(play){
				Jb5.setEnabled(true);
				Jb6.setEnabled(true);
				Jp1.setEnabled(false);
				btnNewButton1.setEnabled(false);
				jb2.setEnabled(false);
				Jb3.setEnabled(false);
				//Jb4.setEnabled(false);
				btnNewButton.setEnabled(false);
				for(int i=0;i<8;i++){
					for(int j=0;j<8;j++){
							chessspot[i][j]=0;
					}
				}
				chessspot[3][3]=chessspot[4][4]=2;
				chessspot[3][4]=chessspot[4][3]=1;
				Jp1.repaint();
				playIndex=0;
				forPlay=processRecord(playArea.getText());
				}else{
					Jb5.setEnabled(!true);
					Jb6.setEnabled(!true);
					Jp1.setEnabled(!false);
					btnNewButton1.setEnabled(!false);
					jb2.setEnabled(!false);
					Jb3.setEnabled(!false);
					//Jb4.setEnabled(false);
					btnNewButton.setEnabled(!false);
					for(int i=0;i<8;i++){
						for(int j=0;j<8;j++){
								chessspot[i][j]=0;
						}
					}
					chessspot[3][3]=chessspot[4][4]=2;
					chessspot[3][4]=chessspot[4][3]=1;
					chessspot[3][2]=chessspot[2][3]=chessspot[5][4]=chessspot[4][5]=4;
					Jp1.repaint();
					playArea.setText("");
				}
			}
			
		});
		
		Jp1=new JDrawPanel();
		Jp1.setBackground(Color.green);
		Jp1.setBounds(53, 107, l*8, l*8);
		contentPane.add(Jp1);
        Jp1.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO �Զ����ɵķ������
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO �Զ����ɵķ������
				if(!play){
				Wnumber=0;
				Bnumber=0;
				int p1=countChess();
				if(ChessAction.processAction(e.getX(), e.getY())!=null){
					int[] p=ChessAction.processAction(e.getX(), e.getY());
					int x=p[0];
					int y=p[1];
					System.out.println("x,y= "+x+" , "+y );
					chessspot[x][y]=count%2+1;
				    boolean z=ChessAction.pocessChess(x, y, count%2+1,true,chessspot);
				    count ++;
				    int t=countChess();
				    if(key!=t){
						if(count%2+1==2){
							playArea.append("white"+"("+x+","+y+")"+"\n");
							key=countChess();
						}else{
							playArea.append("black"+"("+x+","+y+")"+"\n");
							key=countChess();
						}
						}
				    
				    if(p1==t){
				    	count--;
				    }
				    ChessAction.createHint(count%2+1,chessspot);
				    if(!true&&z){
				    //int[] d=new int[2];
				    //d=AI.test(chessspot);
				    //p=ChessAction.processAction(d[0]*l, d[1]*l);
					x=p[0];
					y=p[1];
					System.out.println("x,y= "+x+" , "+y );
					chessspot[x][y]=count%2+1;
				    z=ChessAction.pocessChess(x, y, count%2+1,true,chessspot);
				    //count ++;
				    t=countChess();
				    if(key!=t){
						if(count%2+1==2){
							playArea.append("white"+"("+x+","+y+")"+"\n");
							key=countChess();
						}else{
							playArea.append("black"+"("+x+","+y+")"+"\n");
							key=countChess();
						}
						}
				    
				    if(p1==t){
				    	count--;
				    }
				    ChessAction.createHint(count%2+1,chessspot);
				    if(key!=t){
						if(count%2+1==2){
							playArea.append("white"+"("+x+","+y+")"+"\n");
							key=countChess();
						}else{
							playArea.append("black"+"("+x+","+y+")"+"\n");
							key=countChess();
						}
						}
				    Jp1.repaint();
				}
				Jp1.repaint();
    	        for(int i[]: chessspot){
    	        	for(int t1: i){
    	        		if(t1==1){
    	        			Wnumber++;
    	        		}
    	        		if(t1==2){
    	        			Bnumber++;
    	        		}
    	        	}
    	        }
    	        textField.setText(new Integer(Bnumber).toString());
    	        textField_1.setText(new Integer(Wnumber).toString());
    	        if(AI.isWin(chessspot,count)){
    	        	isWin=AI.isWin(chessspot,count);
    	        	if(Wnumber<Bnumber){
    	        		new Winnote("Black wins!").setVisible(true);
    	        	}
    	        	if(Wnumber>Bnumber){
    	        		new Winnote("White wins!").setVisible(true);
    	        	}
    	        	if(Wnumber==Bnumber){
    	        		new Winnote("Draw level!").setVisible(true);
    	        	}
    	        }
			}
			}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO �Զ����ɵķ������
	
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO �Զ����ɵķ������
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO �Զ����ɵķ������
				
			}
    		 
    	 });
        playArea=new JTextArea();
		playArea.setEditable(false);
		playArea.setBounds(510, 407,163, 200);
		/*for(int i=1;i<100;i++){
		playArea.append("hello test"+i+"\n");
		}*/
		JScrollPane Jp2=new JScrollPane(playArea);
		Jp2.setBounds(510, 407,163, 200);
		contentPane.add(Jp2);
		
		textField = new JTextField();
		textField.setBounds(99, 13, 86, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(409, 13, 86, 24);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblBlack = new JLabel("Black");
		lblBlack.setBounds(14, 16, 72, 18);
		contentPane.add(lblBlack);
		
		JLabel lblWhite = new JLabel("White");
		lblWhite.setBounds(323, 16, 72, 18);
		contentPane.add(lblWhite);
		textField.setEditable(false);
		textField_1.setEditable(false);
		textField.setBackground(Color.YELLOW);
		textField_1.setBackground(Color.YELLOW);
		textField.setText("2");
		textField_1.setText("2");
	}
}

