window.addEventListener('DOMContentLoaded', event => {
    // Simple-DataTables
    // https://github.com/fiduswriter/Simple-DataTables/wiki

    const datatablesSimple = document.getElementById('dataTables');

    if (datatablesSimple) {
        new simpleDatatables.DataTable(datatablesSimple, {
            labels: {
                placeholder: "검색",
                perPage: "",
                info: "{start} 부터 {end} 까지. 총 {rows}개의 데이터.",
                noRows: "현재 데이터가 없습니다.",
                noResults: "일치하는 검색결과가 없습니다."
            }
        });
    }
});