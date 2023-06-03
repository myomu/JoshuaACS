window.addEventListener('DOMContentLoaded', event => {
    // Simple-DataTables
    // https://github.com/fiduswriter/Simple-DataTables/wiki

    const datatablesSimple = document.getElementById('dataTables');
    if (datatablesSimple) {
        new simpleDatatables.DataTable(datatablesSimple);
    }
});