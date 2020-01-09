const path = require('path');

module.exports = {
    entry: {
        app: ['./src/js/app.js']
    },
    output:  {
        path: path.resolve(__dirname, 'src'),
        filename: 'js/index.js'
    },
    module: {
        rules: [
            {
              test: /\.css$/i,
              use: ['style-loader', 'css-loader'],
            },
            {
                test: /\.js$/,
                exclude: /node_modules/,
                use: {
                    loader: 'babel-loader'
                }
            },
          ],
    },
    devServer: {
        contentBase: './src'
    }
};