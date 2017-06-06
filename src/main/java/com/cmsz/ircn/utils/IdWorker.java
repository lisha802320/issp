package com.cmsz.ircn.utils;

import java.util.Date;
import java.util.Random;

import com.cmsz.ircn.common.IrcnConstant;

public class IdWorker {
	private long workerIdBits = 16L;
	private long datacenterIdBits = 1L;
	private long sequenceBits;
	private long maxWorkerId = -1L ^ (-1L << workerIdBits);
	private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

	private long workerIdShift;
	private long datacenterIdShift;
	private long sequenceMask;

	private long workerId;
	private long datacenterId;
	private long sequence = 0L;
	private String lastTimestamp = "";
	private String format;

	public static IdWorker worker;// 14+12位id生成器

	private Long maxSequenceId;

	public IdWorker(Long maxSequenceId) {
		this.maxSequenceId = maxSequenceId;
		this.format = "%0" + maxSequenceId.toString().length() + "d";
	}

	public String nextId() {
		Double seed = Math.random();
		Long nextId = (long)(seed*maxSequenceId);
		String timestamp = timeGen();
		return timestamp
				+ String.format(format, nextId);
	}

	// public IdWorker(long workerId, long datacenterId, Long maxSequenceId) {
	// if (workerId > maxWorkerId || workerId < 0) {
	// throw new IllegalArgumentException(String.format("节点id超出范围",
	// maxWorkerId));
	// }
	// if (datacenterId > maxDatacenterId || datacenterId < 0) {
	// throw new IllegalArgumentException(String.format("集群id超出范围",
	// maxDatacenterId));
	// }
	// this.workerId = workerId;
	// this.datacenterId = datacenterId;
	// this.sequenceBits = Long.toBinaryString(maxSequenceId).length() -
	// workerIdBits - datacenterIdBits;
	// this.workerIdShift = sequenceBits;
	// this.datacenterIdShift = sequenceBits + workerIdBits;
	// this.sequenceMask = -1L ^ (-1L << sequenceBits);
	// this.format = "%0" + maxSequenceId.toString().length() + "d";
	// }
	//
	// public synchronized String nextId() {
	// String timestamp = timeGen();
	// if (timestamp.compareTo(lastTimestamp) < 0) {
	// throw new RuntimeException(String.format("时钟倒退"));
	// }
	// if (timestamp.equals(lastTimestamp)) {
	// sequence = (sequence + 1) & sequenceMask;
	// if (sequence == 0) {
	// timestamp = tilNextMillis(lastTimestamp);
	// }
	// } else {
	// sequence = 0L;
	// }
	// lastTimestamp = timestamp;
	// return timestamp
	// + String.format(format, ((datacenterId << datacenterIdShift) | (workerId
	// << workerIdShift) | sequence));
	// }

	protected String tilNextMillis(String lastTimestamp) {
		String timestamp = timeGen();
		while (timestamp.compareTo(lastTimestamp) <= 0) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	protected String timeGen() {
		return DateUtils.formatDateToYYYYMMddHHmmss(new Date());
	}

	public static void main(String[] args) {
		System.out.println(new Date());
		IdWorker idWorker = new IdWorker(100000000000L);
		for (int i = 0; i < 100000; i++) {
			System.out.println(idWorker.nextId());
		}

		System.out.println(new Date());
	}
}
