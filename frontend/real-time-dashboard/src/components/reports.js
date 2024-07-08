import React, { useEffect, useState } from "react";
import { Form, Button, Table } from 'react-bootstrap';

const Reports = () => {
  const [selectValue, setSelectValue] = useState('');
  const [inputValue, setInputValue] = useState('');
  const [transactions, setTransactions] = useState([]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      var url = 'http://localhost:8083/report/transactions'
      if(selectValue != '') {
        url += `?filter=${selectValue}&value=${inputValue}`;
      }
      fetch(url)
        .then(response => response.json())
        .then(data => setTransactions(data));
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

  return (
    <div className="container-fluid">
      <div className="row">
        <h5>Transactions</h5>
        <Form onSubmit={handleSubmit}>
          <Form.Group controlId="formSelect">
            <Form.Label>Filter</Form.Label>
            <Form.Control as="select" value={selectValue} onChange={(e) => setSelectValue(e.target.value)}>
              <option value="">Choose...</option>
              <option value="user">User</option>
              <option value="status">Status</option>
              <option value="id">Transaction ID</option>
            </Form.Control>
          </Form.Group>
          <Form.Group controlId="formInput">
            <Form.Label>Value</Form.Label>
            <Form.Control type="text" value={inputValue} onChange={(e) => setInputValue(e.target.value)} />
          </Form.Group>
          <Button variant="primary" type="submit">Filtrar</Button>
        </Form>
      </div>
      <div className="row">
        <div className="col-md-12 p-6">
          <Table striped bordered hover>
          <thead>
              <tr>
                <th>Transaction ID</th>
                <th>Time</th>
                <th>User</th>
                <th>Amount</th>
                <th>Status</th>
                <th>Location</th>
              </tr>
          </thead>
          <tbody>
              {transactions.map(tx => (
              <tr key={tx.transaction_id}>
                  <td>{tx.transaction_id}</td>
                  <td>{tx.txtime}</td>
                  <td>{tx.user}</td>
                  <td>{tx.amount}</td>
                  <td>{tx.status}</td>
                  <td>{tx.location}</td>
              </tr>
              ))}
          </tbody>
          </Table>
        </div>
      </div>
    </div>
  );
};

export default Reports;
