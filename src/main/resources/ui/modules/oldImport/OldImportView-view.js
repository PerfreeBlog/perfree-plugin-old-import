function v(m) {
  return axios.post("/api/auth/plugin/oldImport/oldVersionImport", m);
}
const c = window.Vue.createTextVNode, r = window.Vue.resolveComponent, o = window.Vue.withCtx, a = window.Vue.createVNode, b = window.Vue.unref, N = window.Vue.openBlock, P = window.Vue.createElementBlock, I = { class: "page" }, U = window.Vue.reactive, n = window.Vue.ref, V = window.ElementPlus.ElMessage, x = {
  __name: "OldImportView",
  setup(m) {
    let u = n(!1);
    const l = n({
      dataBaseIp: "",
      dataBasePort: "",
      dataBaseName: "",
      dataBaseUserName: "",
      dataBasePassword: ""
    }), f = U({
      dataBaseIp: [{ required: !0, message: "请输入老版本数据库IP", trigger: "blur" }],
      dataBasePort: [{ required: !0, message: "请输入老版本数据库端口", trigger: "blur" }],
      dataBaseName: [{ required: !0, message: "请输入老版本数据库名称", trigger: "blur" }],
      dataBaseUserName: [{ required: !0, message: "请输入老版本数据库账户", trigger: "blur" }],
      dataBasePassword: [{ required: !0, message: "请输入老版本数据库密码", trigger: "blur" }]
    }), p = n();
    function w() {
      p.value.validate((i) => {
        i && (u.value = !0, v(l.value).then((e) => {
          e.code === 200 ? V.success("导入完成") : V.success("导入失败"), u.value = !1;
        }));
      });
    }
    return (i, e) => {
      const B = r("el-alert"), d = r("el-form-item"), s = r("el-input"), _ = r("el-button"), g = r("el-form");
      return N(), P("div", I, [
        a(B, {
          type: "warning",
          closable: !1
        }, {
          default: o(() => e[5] || (e[5] = [
            c(" 注: 只会导入分类、标签、文章、友链、附件、用户、评论等数据库数据，如数据已存在则会更新， 附件资源请手动将老版本根目录下resources/upload/attach目录中的内容复制到当前版本根目录resources/upload目录中, 以上步骤操作完毕后,如出现附件访问不到的问题,可在系统管理->附件存储策略中调整默认存储策略中的存储路径位置 ")
          ])),
          _: 1
        }),
        a(g, {
          ref_key: "addFormRef",
          ref: p,
          model: l.value,
          "label-width": "120px",
          "status-icon": "",
          rules: f
        }, {
          default: o(() => [
            a(d),
            a(d, {
              label: "数据库IP",
              prop: "dataBaseIp"
            }, {
              default: o(() => [
                a(s, {
                  modelValue: l.value.dataBaseIp,
                  "onUpdate:modelValue": e[0] || (e[0] = (t) => l.value.dataBaseIp = t),
                  placeholder: "请输入老版本数据库IP"
                }, null, 8, ["modelValue"])
              ]),
              _: 1
            }),
            a(d, {
              label: "数据库端口",
              prop: "dataBasePort"
            }, {
              default: o(() => [
                a(s, {
                  modelValue: l.value.dataBasePort,
                  "onUpdate:modelValue": e[1] || (e[1] = (t) => l.value.dataBasePort = t),
                  placeholder: "请输入老版本数据库端口"
                }, null, 8, ["modelValue"])
              ]),
              _: 1
            }),
            a(d, {
              label: "数据库名称",
              prop: "dataBaseName"
            }, {
              default: o(() => [
                a(s, {
                  modelValue: l.value.dataBaseName,
                  "onUpdate:modelValue": e[2] || (e[2] = (t) => l.value.dataBaseName = t),
                  placeholder: "请输入老版本数据库名称"
                }, null, 8, ["modelValue"])
              ]),
              _: 1
            }),
            a(d, {
              label: "数据库账户",
              prop: "dataBaseUserName"
            }, {
              default: o(() => [
                a(s, {
                  modelValue: l.value.dataBaseUserName,
                  "onUpdate:modelValue": e[3] || (e[3] = (t) => l.value.dataBaseUserName = t),
                  placeholder: "请输入老版本数据库账户"
                }, null, 8, ["modelValue"])
              ]),
              _: 1
            }),
            a(d, {
              label: "数据库密码",
              prop: "dataBasePassword"
            }, {
              default: o(() => [
                a(s, {
                  modelValue: l.value.dataBasePassword,
                  "onUpdate:modelValue": e[4] || (e[4] = (t) => l.value.dataBasePassword = t),
                  placeholder: "请输入老版本数据库密码"
                }, null, 8, ["modelValue"])
              ]),
              _: 1
            }),
            a(d, null, {
              default: o(() => [
                a(_, {
                  type: "primary",
                  onClick: w,
                  loading: b(u)
                }, {
                  default: o(() => e[6] || (e[6] = [
                    c("开始导入")
                  ])),
                  _: 1
                }, 8, ["loading"])
              ]),
              _: 1
            })
          ]),
          _: 1
        }, 8, ["model", "rules"])
      ]);
    };
  }
};
export {
  x as default
};
