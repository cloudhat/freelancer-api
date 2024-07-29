<h1>프리랜서 검색 서비스</h1>
<p><a href="https://drive.google.com/file/d/1oiyXZs29MaElTIH_Ch1IkHHNNrr3mcgn/view"> Executable jar 다운로드 링크</a></p>

<h2>0.공통사항</h2>
<ul class="bulleted-list">
    <li>Service Layer와 Repository Layer 별로 테스트를 구현하고 Controller Layer는 E2E 테스트로 보완했습니다.</li>
    <li> 과제 요구사항 기준으로는 오버엔지니어링일 수 있으나 견고한 테스트 방법을 구체적으로 보이기 위해 의도적으로 테스트를 꼼꼼하게 작성하였습니다</li>
</ul>
<h2>1.프리랜서 검색 </h2>
<ul class="bulleted-list">
    <li> QueryDSL을 사용하여 유연하게 확장 가능한 Repository를 구현했습니다</li>
    <li> 프리랜서 엔티티와 프리랜서의 특징(기술능력,조회수) 엔티티를 분리했습니다.</li>
    <ul class="bulleted-list">
        <li> 제 경험 상 '회원', '상품' 등의 핵심 엔티티는 가능한 작게 유지하고 관련 필드는 별도의 테이블에서 관리하는 것이 유지, 확장 면에서 바람직했기 때문입니다. </li>
        <li> 구체적으로는, 서비스가 성장함에 따라 다양한 필드가 추가되어야 하나 '회원', '상품' 등의 핵심 엔티티에 필드를 추가할 경우 핵심 엔티티들이 지나치게 많은 책임을 가지게 되어 변경하기 어렵게 되기 때문입니다.  </li>
    </ul>
</ul>
<h2>2.인기 검색어 </h2>
<ul class="bulleted-list">
    <li> Redis의 자료구조 Sorted Set을 이용하여 인기검색어를 저장, 조회할 수 있도록 구현했습니다</li>
    <ul class="bulleted-list">
        <li> 인메모리 DB인 Redis의 특성 상 빠른 속도 덕분에 대용량 트래픽에 대처할 수 있고</li>
        <li> 싱글쓰레드이기 때문에 RDB와 비교해서 비교적 성능 저하 없이 동시성 문제에서 자유로운 장점을 고려하여 Redis를 선택하였습니다.</li>
    </ul>
    <li> 일반적으로 인기검색어는 과거 검색 내역을 분석하여 회원 별로 알맞은 인기검색어를 제안해야 합니다 (ex: 연령별, 직업별 등)</li>
      <ul class="bulleted-list">
            <li> 따라서 일단 요구사항에 맞게 단순하게 조회 수 순으로 인기검색어 API를 구현하였으나 </li>
            <li> PopularSearchedJobService.saveSearchJobLog() 메소드로 검색내역을 별도로 DB에 저장해야 함을 명시했습니다 </li>
        </ul>
</ul>
