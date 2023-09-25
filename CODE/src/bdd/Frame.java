package bdd;

public class Frame {

	private PageId pid;
	private int pin_count;
	private boolean flag_dirty = false;
	private byte[] buffer;
	private long lastUse;
	
	public Frame() {
		buffer = new byte[DBParams.pageSize]; 
		pid = new PageId(-1,-1);
		flag_dirty = false;
	}

	public PageId getPid() {
		return pid;
	}

	public void setPid(PageId pid) {
		this.pid = pid;
	}

	public int getPin_count() {
		return pin_count;
	}

	public void setPin_count(int pin_count) {
		this.pin_count = pin_count;
	}

	public boolean isFlag_dirty() {
		return flag_dirty;
	}

	public void setFlag_dirty(boolean flag_dirty) {
		this.flag_dirty = flag_dirty;
		lastUse = System.nanoTime();
	}

	public byte[] getBuffer() {
		return buffer;
	}

	public void setBuffer(byte[] buffer) {
		this.buffer = buffer;
		lastUse = System.nanoTime();
	}

	public void incrementPinCount() {
		pin_count++;
	}

	public void decrementPinCount() {
		pin_count--;
	}

	public long getLastUse() {
		return lastUse;
	}

	public void reset(){
		this.setPin_count(0);
		this.setFlag_dirty(false);
		this.setPin_count(0);
		buffer = new byte [DBParams.pageSize];
	}

}