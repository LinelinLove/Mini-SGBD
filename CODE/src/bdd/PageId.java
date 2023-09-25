package bdd;
import java.io.Serializable;
import java.util.Objects;

public class PageId implements Serializable {
    private static final long serialVersionUID = 1L;
	private int FileIdx;
	private int PageIdx;

	public PageId(int FileIdx, int PageIdx){
		this.FileIdx = FileIdx;
		this.PageIdx = PageIdx;
	}

	public int getFileIdx(){
		return FileIdx;
	}

	public void setFileIdx(int fileIdx) {
		FileIdx = fileIdx;
	}

	public int getPageIdx(){
		return PageIdx;
	}

	public void setPageIdx(int pageIdx) {
		PageIdx = pageIdx;
	}

	@Override
	public String toString() {
		return "(" + FileIdx + ", " + PageIdx + ")";
	}
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof PageId)) {
			return false;
		}
		PageId pageId = (PageId) o;
		return FileIdx == pageId.FileIdx && PageIdx == pageId.PageIdx;
	}

	@Override
	public int hashCode() {
		return Objects.hash(FileIdx, PageIdx);
	}

}