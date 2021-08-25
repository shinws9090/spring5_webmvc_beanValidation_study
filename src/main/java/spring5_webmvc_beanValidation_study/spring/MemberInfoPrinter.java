package spring5_webmvc_beanValidation_study.spring;

public class MemberInfoPrinter {
	
	private MemberDao memberDao;
	
	private MemberPrinter printer;
	
	public void printMemberInfo(String email) {
		Member member = memberDao.selectByEmail(email);
		if(member==null) {
			System.out.println("데이터 없음");
			return;
		}
		printer.print(member);
		System.out.println();
	}

//	public void setMemberDao(MemberDao memberDao) {
//		this.memberDao = memberDao;
//	}
//
//	public void setPrinter(MemberPrinter printer) {
//		this.printer = printer;
//	}
//	
	
//85쪽 키포인트는 세터메소트
}
