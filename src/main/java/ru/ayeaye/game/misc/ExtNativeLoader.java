package ru.ayeaye.game.misc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExtNativeLoader {
	
	private static List<String> windowsLibs32 = new ArrayList<>();
	private static List<String> windowsLibs64 = new ArrayList<>();
	private static List<String> linuxLibs32 = new ArrayList<>();
	private static List<String> linuxLibs64 = new ArrayList<>();
	private static List<String> osxLibs = new ArrayList<>();
	
	static {
		// windows
		windowsLibs32.add("jinput-dx8.dll");
		windowsLibs32.add("jinput-raw.dll");
		windowsLibs32.add("lwjgl.dll");
		windowsLibs32.add("OpenAL32.dll");
		
		windowsLibs64.add("jinput-dx8_64.dll");
		windowsLibs64.add("jinput-raw_64.dll");
		windowsLibs64.add("lwjgl64.dll");
		windowsLibs64.add("OpenAL64.dll");
		
		// linux
		linuxLibs32.add("libjinput-linux.so");
		linuxLibs32.add("liblwjgl.so");
		linuxLibs32.add("libopenal.so");
		
		linuxLibs64.add("libjinput-linux64.so");
		linuxLibs64.add("liblwjgl64.so");
		linuxLibs64.add("libopenal64.so");
		
		// osx
		osxLibs.add("libjinput-osx.dylib");
		osxLibs.add("liblwjgl.dylib");
		osxLibs.add("openal.dylib");
	}
	
	
	public ExtNativeLoader() {
	}
	
	public void loadLibraries() throws IOException {
		
		StringBuilder packageToNatives = new StringBuilder("natives/");
		
        final String osArch = System.getProperty("os.arch");
        final String osName = System.getProperty("os.name").toLowerCase();
        
        List<String> libs;
        switch (osName) {
        case "windows":
        	packageToNatives.append("windows/");
        	if (osArch.equalsIgnoreCase("x86")) {
        		libs = windowsLibs32;
        	} else {
        		libs = windowsLibs64;
        	}
        	break;
        case "linux":
        	packageToNatives.append("linux/");
        	if (osArch.equalsIgnoreCase("x86")) {
        		libs = linuxLibs32;
        	} else {
        		libs = linuxLibs64;
        	}
        	break;
        case "mac os x":
        	packageToNatives.append("osx/");
        	libs = osxLibs;
        	break;
    	default:
    		throw new IllegalStateException("Unknowen OS type " + osName);
        }
        
        String tmpDirName = System.getProperty("java.io.tmpdir");
        File tmpDir = new File(tmpDirName);
        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }
        tmpDir.deleteOnExit();
        
        for (String lib: libs) {
        	File file = File.createTempFile(packageToNatives.toString() + lib, "", tmpDir);
        	file.deleteOnExit();
        }
	}
	
	public static void main(String[] args) throws Exception {
		new ExtNativeLoader().loadLibraries();
	}
}
