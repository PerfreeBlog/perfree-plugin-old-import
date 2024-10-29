export function oldVersionImportApi(data) {
    return axios.post('/api/auth/plugin/oldImport/oldVersionImport', data);
}