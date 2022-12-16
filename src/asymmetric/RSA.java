package asymmetric;

import java.io.PrintStream;
import java.math.BigInteger;

public class RSA
{
	public static void decrypt_n1_n2() {
		BigInteger e1 = new BigInteger("421");
		BigInteger n1 = new BigInteger("130918180062915485900954597631313629864836201978227133319187624210227148776154322707071245742980686741118030867148438370439660292136998104360860705032542533349698053368186836516200888554212669145740474501485880521654642182193789294300080988945147087020219803028448157467640485725166289781136027695109151280023");
		BigInteger c1 = new BigInteger("125668980654191025766106245989834975541613169863428144696755807424629265957348677081442923408559618380442192854729350775391879836843016109151109777986201983030988231536553290463075317639660018767421348298817260834266597294062885097758664300963710403247529713499748336229122637856959094917789956680302377041648");
		BigInteger e2 = new BigInteger("4294967297");
		BigInteger n2 = new BigInteger("105044834493354870427083677016839461592992165240096692747718987052050266455031262116601551194381319276801214817469383111105004997120903526991683696718929079263900746731646833343503145942316362644131024923877835116209542653949534087434706912850426335055450650929515886153502476201279214508628867935210759638049");
		BigInteger c2 = new BigInteger("103273846318374013847133354454290018252841929281701785182854874121750536934868148446653623008445332820687295608902200316557020917926914901219659667428744249546181662616934195284064649739836332027719202301874146042110269140295945419182895395818244166770597545423932126647155796475140896032200039223347369965796");
		BigInteger p1 = n1.gcd(n2);
		BigInteger q1 = n1.divide(p1);
		BigInteger h1 = p1.subtract(BigInteger.ONE).multiply(q1.subtract(BigInteger.ONE));
		BigInteger d1 = e1.modInverse(h1);
		System.out.println(new String(c1.modPow(d1, n1).toByteArray()));
	}
	
	public static void decrypt_short() {
		BigInteger p = new BigInteger("264208679307705732524907225971531207681");
		BigInteger q = new BigInteger("200181170185227101268806368199715987557");
		BigInteger e = new BigInteger("1031");
		BigInteger ct = new BigInteger("46903772711485649870400600542340635647113782148471559341585401119110429267342");
		BigInteger n = p.multiply(q);
		BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		BigInteger d = e.modInverse(phi);
		BigInteger pt = ct.modPow(d, n);
		System.out.println(new String(pt.toByteArray()));
	}
	
	public static void decrypt_original() {
		// Generate the RSA Key Material
		PrintStream out = System.out;
		out.println("--------------------------------Generate RSA Key Material");
		int cert = 20;
		BigInteger one = BigInteger.ONE;
		
		BigInteger p = new BigInteger("264208679307705732524907225971531207681");
		BigInteger q = new BigInteger("200181170185227101268806368199715987557");
		// Sanity check
		out.println("--------------------------------Sanity check on p/q");
		if (p.isProbablePrime(cert) && q.isProbablePrime(cert))
			out.println("p/q are good");
		else
			out.println("p/q are not prime !!!");
		//--------------------------------------------
		BigInteger n = p.multiply(q);
		BigInteger phi = p.subtract(one);
		phi = phi.multiply(q.subtract(one));
		
		BigInteger e = new BigInteger("7");
		// Sanity check
		out.println("--------------------------------Sanity check on e");
		if (e.gcd(phi).equals(one))
			out.println("e is good");
		else
		out.println("e not co-prime with phi");
		//--------------------------------------------
		BigInteger d = e.modInverse(phi);
		
		// Sanity check
		out.println("--------------------------------Sanity check on e/d");
		if (e.multiply(d).mod(phi).equals(one))
			out.println("e/d are reciprocals");
		else
			out.println("e/d are no good !!!");
		//--------------------------------------------
		out.println("--------------------------------Components");
		
		out.println("p = " + p);
		out.println("q = " + q);
		out.println("n = " + n);
		out.println("e public = " + e);
		out.println("d = " + d);
		out.println("phi = " + phi);
		
		// --------------------------------Use RSA
		out.println("--------------------------------Use RSA");
		int msg = 2;
		out.println("Message: " + msg);
		BigInteger m = new BigInteger("2");
		// Sanity check
		out.println("--------------------------------Sanity check on m");
		if (m.compareTo(n) < 0)
			out.println("m is < n");
		else
			out.println("m too big !!!");
		//--------------------------------------------
		out.println("--------------------------------Decrypt");
		BigInteger ct = m.modPow(e, n);
		out.println("Cyper Text: " + ct);
		BigInteger received = ct.modPow(d, n);
		out.println("Decrypt: " + received);
		out.println("To String: " + new String(received.toByteArray()));
		
	}
	
	public static void C_PQ_1() {
		BigInteger e = new BigInteger("74327");
		BigInteger ct = new BigInteger("87014856975716299121085087309577038316883175412853820115551293556230488405826385706604303724175236985573832006395540199066061101502996745421485579743246846982636317440505885092956723199407403632041108913018671613508572002898008615700858579079601105011909417884801902333329415712320494308682279897714456370814");
		BigInteger n = new BigInteger("94587468335128982981605019776781234618384857805657005686084562260910788622013722070926491690843853690071248130134427832324966728582532832363221542231787068203763027067400082835394459857525017707284768411819006776211493735326500782954621660256501187035611332577696332459049538105669711385995976912007767106063");
		BigInteger d = new BigInteger("7289370196881601766768920490284861650464951706793000236386405648425161747775298344104658393385359209126267833888223695609366844098655240542152017354442883676634193191857568369042999854440242050353181703706753485749165295123694487676952198090537385200990850805837963871485320168470788328336240930212290450023");
		BigInteger pt = ct.modPow(d, n);
		System.out.println(new String(pt.toByteArray()));
	}
	
	public static void C_PQ_2() {
		BigInteger p = new BigInteger("10358344307803887695931304169230543785620607743682421994532795393937342395753127888522373061586445417642355843316524942445924294144921649080401518286829171");
		BigInteger e = new BigInteger("74327");
		BigInteger ct = new BigInteger("10870101966939556606443697147757930290262227730644958783498257036423105365610629529910525828464329792615002602782366786531253275463358840412867833406256467153345139501952173409955322129689670345445632775574301781800376545448990332608558103266831217073027652061091790342124418143422318965525239492387183438956");
		BigInteger n = new BigInteger("94587468335128982981605019776781234618384857805657005686084562260910788622013722070926491690843853690071248130134427832324966728582532832363221542231787068203763027067400082835394459857525017707284768411819006776211493735326500782954621660256501187035611332577696332459049538105669711385995976912007767106063");
		
		BigInteger q = n.divide(p);
		BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		BigInteger d = e.modInverse(phi);
		BigInteger pt = ct.modPow(d, n);
		System.out.println(new String(pt.toByteArray()));
	}
	
	public static void C_PQ3() {
		BigInteger phi = new BigInteger("8584037913642434144111279062847405921823163865842701785008602377400681495147541519557274092429073976252689387304835782258785521935078205581766754116919200");
		BigInteger q = new BigInteger("87020952829623092932322362936864583897972618059974315662422560067745889600571");
		BigInteger e = new BigInteger("65537");
		BigInteger ct = new BigInteger("1817487313698347891034157970684926175211834109573277793102901854482611726141560963120214926234448852417078321539316776648109260519063106558303669880226359");
		BigInteger p = phi.add(BigInteger.ONE).divide(q.subtract(BigInteger.ONE));
		BigInteger n = q.multiply(p);
		BigInteger d = e.modInverse(phi);
		BigInteger pt = ct.modPow(d, n);
		System.out.println(new String(pt.toByteArray()));
	}
	
	public static void Miller_Rabin() {
		BigInteger base = BigInteger.TWO;
		BigInteger x = new BigInteger("1033931178476059651954862004553");
		BigInteger fermet = base.modPow(x.subtract(BigInteger.ONE), x);
		if (!fermet.equals(BigInteger.ONE)) {
			System.out.println("Not Prime");
			return;
		}
		BigInteger e = x.subtract(BigInteger.ONE);
		boolean cond = true;
		while (cond) {
			if (base.modPow(e.divide(BigInteger.TWO), base).equals(BigInteger.ONE)) {
				e = e.subtract(BigInteger.ONE);
				continue;
			} else if (base.modPow(e.divide(BigInteger.TWO), base).equals(BigInteger.ZERO.subtract(BigInteger.ONE))) {
				System.out.println("Inconclusive");
				cond = false;
				continue;
			}
			System.out.println("Composite");
			cond = false;
		}
	}

	public static void main(String[] args)
	{
		
	}

}
