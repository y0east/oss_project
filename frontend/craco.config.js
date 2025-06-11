// craco.config.js (package.json과 같은 폴더)
console.log('💡 CRACO config loaded');

module.exports = {
  devServer: {
    historyApiFallback: {
      disableDotRule: true,
      rewrites: [
        { from: /^\/$/, to: '/main_page/main_page.html' },
        { from: /^\/notice$/, to: '/notice_page/notice_page.html' },
        { from: /^\/login$/, to: '/login_page/login_page.html' },
        { from: /./, to: '/404.html' },
      ],
    },
  },
};
