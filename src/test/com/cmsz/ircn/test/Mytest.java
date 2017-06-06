package com.cmsz.ircn.test;

import org.apache.commons.codec.digest.DigestUtils;

import jdk.internal.org.objectweb.asm.commons.Method;

public class Mytest {

	public static void main(String[] args) {
		new Mytest().test();
	}
	
	public void test(){
		
		System.out.println(DigestUtils.md5Hex("123456"));
	}

}
