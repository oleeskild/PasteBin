package implementation;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PasteBin {
	public static void main(String[] args){
		
		Document doc = null;
		
		Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
		String pasteCode;
		try {
			//Retrieve the text currently in clipboard
			pasteCode = (String) clpbrd.getData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException | IOException e1) {
			return;
		}
		if(pasteCode == null || pasteCode.equals(""))
			return;
		try {
			//Post all the necessary data
			doc = Jsoup.connect("http://pastebin.com/post.php")
					.data("paste_code", pasteCode)
					.data("paste_format", "1")
					.data("paste_expire_date", "N")
					.data("paste_private", "1")
					.data("submit_hidden", "submit_hidden")
					.data("post_key", "")
					.data("paste_expire_date", "N")
					.data("paste_name", "")
					.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
					.post();
		} catch (IOException e) {
			System.out.println("Couldn't connect to pastebin");
			System.exit(0);
		}
	
		clpbrd.setContents(new StringSelection(doc.select("meta[property=og:url]").attr("content").toString()), null);
		System.exit(0);

	}
}
