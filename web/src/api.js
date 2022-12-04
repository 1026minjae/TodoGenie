//Server <-> Client communication API
import axios from 'axios';
import CryptoJS from "crypto-js";

const server_url = "http://localhost:8000";

const axiosInstance = axios.create({
    headers: {
        'Access-Control-Allow-Origin': 'https://localhost:8000'
    },
    baseURL: server_url
});

const register = (username, id, pw, success, fail) => {
    const data = {
        userName: username,
        userId: id,
        password: pw
    };
    
    axiosInstance.post((server_url + "/users/sign_up"), data).then((res) => {
        if (res.data === "user name already exists") {
            fail();
        }
        else {
            success();
        }
        //console.log(res);
    }).catch((e) => {
        console.log("register: "+e);
        fail();
    });
};

const login = (id, pw, success, fail) => {
    const data = {
        userId: id,
        password: pw
    };
    
    axiosInstance.post((server_url + "/users/login"), data).then((res) => {
        console.log(res);
        if (res.data === "Wrong Password") {
            fail("\nPassword is wrong.");
        }
        else if (res.data === "No matching ID") {
            fail("\nID is wrong.");
        }
        else {
            success(res);
        }
    }).catch((e) => {
        console.log("login: "+e);
        fail("");
    });
};

const logout = (success, fail) => {    
    axiosInstance.get((server_url + "/users/logout")).then((res) => {
        if (res.data === "Logged out") {
            success();
        }
        else {
            fail();
        }
    }).catch((e) => {
        console.log("logout: "+e);
        fail();
    });
};

const loadTodo = (start, end, success, fail) => {
    const ps = {
        start: start,
        end: end
    };
    
    axiosInstance.get((server_url + "/todos"), {params: ps}).then((res) => {
        success(res.data);
    }).catch((e) => {
        console.log("loadTodo: "+e);
        fail();
    });
};

const searchTodo = (keyword, success, fail) => {
    const ps = {
        keyword: keyword
    };
    
    axiosInstance.get((server_url + "/todos/search"), {params: ps}).then((res) => {
        success(res.data);
    }).catch((e) => {
        console.log("searchTodo: "+e);
        fail();
    });
};

const addTodo = (title, start, end, success, fail) => {
    const data = {
        title: title,
        start: start,
        end: end
    };
    
    axiosInstance.post((server_url + "/todos"), data).then((res) => {
        console.log(res);
        success();
    }).catch((e) => {
        console.log("addTodo: "+e);
        fail();
    });
};

const editTodo = (id, title, start, end, state, success, fail) => {
    const data = {
        title: title,
        start: start,
        end: end,
        state: state
    };
    
    axiosInstance.put((server_url + "/todos/:" + id), data).then((res) => {
        console.log(res);
        success();
    }).catch((e) => {
        console.log("editTodo: "+e);
        fail();
    });
};

const deleteTodo = (id, success, fail) => {    
    axiosInstance.delete((server_url + "/todos:" + id)).then((res) => {
        console.log(res);
        success();
    }).catch((e) => {
        console.log("deleteTodo: "+e);
        fail();
    });
};

const KEY = "WfH+%ug%G?TT=G/FY9z!6M}aqAQg?]pz";

const saveAuthInfo = (authinfo) => {
    let encrypted_info = CryptoJS.AES.encrypt(JSON.stringify(authinfo), KEY).toString();
    let dt = new Date();
    dt.setDate(dt.getDate()+1);
    document.cookie = "dL7uM4gyk4="+encrypted_info+";path=/;expires="+dt.toUTCString()+";";
}

const searchAuthInfo = () => {
    let regex = new RegExp("dL7uM4gyk4=([^;]*)");
    if (regex.test(document.cookie)) {
        let encrypted_info = regex.exec(document.cookie);
        let decryted_info = CryptoJS.AES.decrypt(encrypted_info[0], KEY).toString();
        return JSON.parse(decryted_info);
    }
    else {
        return null;
    }
}

export {register, login, logout, loadTodo, searchTodo, addTodo, editTodo, deleteTodo, saveAuthInfo, searchAuthInfo};