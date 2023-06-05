<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>网上银行电子回单</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
            max-width: 800px;
            margin: 0 auto;
            font-size: 14px;
            line-height: 1.5;
            color: #333;
        }

        th, td {
            text-align: left;
            padding: 8px;
            border-bottom: 1px solid #ddd;
            vertical-align: top;
        }

        th {
            background-color: #f2f2f2;
            font-weight: bold;
            color: #333;
        }

        tr:hover {
            background-color: #f5f5f5;
        }

        .container {
            margin: 0 auto;
            width: 90%;
            max-width: 800px;
            padding-top: 50px;
            padding-bottom: 50px;
            background-color: #fff;
            border: 1px solid #ddd;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        .logo {
            margin-bottom: 30px;
            text-align: center;
        }

        .logo img {
            max-width: 150px;
            height: auto;
        }

        .header {
            margin-bottom: 30px;
            font-size: 20px;
            font-weight: bold;
            text-align: center;
        }

        .section-header {
            margin-top: 30px;
            margin-bottom: 20px;
            font-size: 16px;
            font-weight: bold;
        }

        .section-body {
            margin-bottom: 30px;
        }

        .section-body p {
            margin-bottom: 10px;
        }

        .footer {
            margin-top: 30px;
            font-size: 12px;
            text-align: center;
            color: #999;
        }

        body{
            font-family:SimHei;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        网上银行电子回单
    </div>
    <div class="section-body">
        <table>
            <tr>
                <th>回单编号:</th>
                <td colspan="3">${receipt_id}</td>
            </tr>
            <tr>
                <th>付款账号：</th>
                <td>${payer_account}</td>
                <th>收款账号：</th>
                <td>${payee_account}</td>
            </tr>
            <tr>
                <th>付款人户名：</th>
                <td>${payer_username}</td>
                <th>收款人户名：</th>
                <td>${payee_username}</td>
            </tr>
            <tr>
                <th>付款人开户行：</th>
                <td>${payer_bank}</td>
                <th>收款人开户行：</th>
                <td>${payee_bank}</td>
            </tr>
            <tr>
                <th>金额(小写):</th>
                <td>${amount_in_figures}</td>
                <th>金额(大写)</th>
                <td>${amount_in_words}</td>
            </tr>
            <tr>
                <th>币种:</th>
                <td>${currency}</td>
                <th>凭证号：</th>
                <td>${voucher_number}</td>
            </tr>
            <tr>
                <th>交易日期：</th>
                <td>${date}</td>
                <th>交易时间：</th>
                <td>${time}</td>
            </tr>
            <tr>
                <th>附言：</th>
                <td colspan="3">${postscript}</td>
            </tr>
        </table>
    </div>
    <div class="footer">
        此为系统自动发送邮件，请勿回复。如有问题，请拨打客服热线：400-123-4567。
    </div>
</div>
</body>
</html>