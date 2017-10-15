package br.com.pierin.pgw.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

public class StringUtil implements Serializable {

	private static final long serialVersionUID = 2236455482605988739L;

	private static final Logger LOG = Logger.getLogger(StringUtil.class);

	public static String buildListString(List<String> itens, String supress) {
		StringBuilder builder = new StringBuilder("");
		

		if (itens != null && !itens.isEmpty()) {
			
			itens.remove(supress);
			
			for (int i = 0; i < itens.size(); i++) {
				String item = itens.get(i);
				if (i == (itens.size() - 1)) {
					builder.append(item);
				} else {
					builder.append(item + ", ");
				}
			}
		}
		return builder.toString();

	}


	public static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			LOG.error(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					LOG.error(e);
				}
			}
		}

		return sb.toString();

	}

	public static String convertListToString(List<String> list) {

		StringBuffer buffer = new StringBuffer();
		buffer.append("[");

		for (int i = 0; i < list.size(); i++) {
			String item = list.get(i);

			if (list.size() == (i + 1)) {
				buffer.append("\"" + item + "\"");
			} else {
				buffer.append("\"" + item + "\",");
			}
		}
		buffer.append("]");
		return buffer.toString();
	}

}
