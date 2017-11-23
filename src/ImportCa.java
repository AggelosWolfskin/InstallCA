import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.io.IOException;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;

public class ImportCa {
	private static DataInputStream dis;

	public static void main(String[] argv) throws Exception {

		String certfile = "C:/cygwin64/home/Γιάννης Λυκοτόμαρος/newcerts/ca.pem"; /* your cert path */
		FileInputStream is = new FileInputStream("C:/Program Files/Java/jre1.8.0_151/lib/security/cacerts");

		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		keystore.load(is, "changeit".toCharArray());

		String alias = "monitorelectronics";
		char[] password = "changeit".toCharArray();

		//////

		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		InputStream certstream = fullStream(certfile);
		Certificate certs = cf.generateCertificate(certstream);

		///
		File keystoreFile = new File("C:/Program Files/Java/jre1.8.0_151/lib/security/cacerts");
		// Load the keystore contents
		FileInputStream in = new FileInputStream(keystoreFile);
		keystore.load(in, password);
		in.close();

		// Add the certificate
		keystore.setCertificateEntry(alias, certs);

		// Save the new keystore contents
		FileOutputStream out = new FileOutputStream(keystoreFile);
		keystore.store(out, password);
		out.close();

	}

	private static InputStream fullStream(String fname) throws IOException {
		FileInputStream fis = new FileInputStream(fname);
		dis = new DataInputStream(fis);
		byte[] bytes = new byte[dis.available()];
		dis.readFully(bytes);
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		return bais;
	}

}
