package me.mallard.kvanity;

import io.github.apemanzilla.kwallet.KristAPI;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.codec.digest.DigestUtils;

import de.itoobi.stringer.Stringer;

public class KVanity {

	public static void main(String[] args) {
		System.out.println("Starting KVanity");

		boolean verbose = false;
		String prefix = "";
		String suffix = "";

		Options options = new Options();
		options.addOption("v", false, "Verbose");
		options.addOption("p", true, "Krist address prefix");
		options.addOption("s", true, "Krist address suffix");

		try {
			CommandLine cmd = new GnuParser().parse(options, args);

			if (!(cmd.hasOption("p") || cmd.hasOption("s"))) {
				System.err.println("ERROR: Requires prefix or suffix option!");
				new HelpFormatter().printHelp("KVanity", options);
				System.exit(-1);
				return;
			}

			if (cmd.hasOption("p"))
				prefix = cmd.getOptionValue("p");

			if (cmd.hasOption("s"))
				prefix = cmd.getOptionValue("s");

			if (cmd.hasOption("v"))
				verbose = true;
		} catch (ParseException e) {
			System.err.println("ERROR: Unknown Error!");
			e.printStackTrace();
			System.exit(-2);
			return;
		}

		Stringer s = new Stringer("UTF-8");

		while (true) {
			String password = s.nextString();
			String address = KristAPI.makeAddressV2(DigestUtils.sha256Hex("KRISTWALLET" + password) + "-000");

			if (address.startsWith(prefix) && address.endsWith(suffix)) {
				System.out.println("Found address " + address + " with password " + password);
				break;
			}

			if (verbose)
				System.out.println("Tried \"" + password + "\" got \"" + address + "\"");
		}

		System.out.println("Thanks!");
	}

}
