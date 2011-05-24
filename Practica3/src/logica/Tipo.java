package logica;


public enum Tipo {
	
	 CP, BS, SN, MP, MM, DU, NOT, EQ;
	 
	 public static double PROB_TERMINAL = 0.09; 
	 
	 public static int getCantHijosDeTipo(Tipo op){
		 if (isFuncion(op)){
			 int resultado=1;
			 switch (op){
			 case DU:
				 resultado=2;
				 break;
			 case EQ:
				 resultado=2;
				 break;
			 }
			return resultado;
		 }
		 return 0;
	}
	 
	 public static boolean isTerminal(Tipo a){
		 return ((a==CP)||(a==BS)||(a==SN));
	 }
	 
	 public static boolean isFuncion(Tipo a){
		 return !isTerminal(a);
	 }
	 
	 public static Tipo getRandTerminal() {
			double p = Math.random();
			
			if (p < 1.0/3) 
				return Tipo.CP;
			if (p < 2.0/3) 
				return Tipo.BS;
			return Tipo.SN;
		}
	 
	 public static Tipo getRandFuncion() {
		double p = Math.random();
	 	if (p < 1.0/5) 
	 		return Tipo.MP;
		if (p < 2.0/5) 
			return Tipo.MM;
		if (p < 3.0/5) 
			return Tipo.DU;
		if (p < 4.0/5) 
			return Tipo.NOT;
		return Tipo.EQ;
	 }
	 
	 public static Tipo getRandTipo() {
			if (Math.random() < PROB_TERMINAL)
				return getRandTerminal();
			
			return getRandFuncion();
		}
	 
	 public static void printErr(String s) {
			System.err.println("ERROR: " + s);
		}

}
