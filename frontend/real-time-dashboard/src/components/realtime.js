import React, { useEffect, useState } from "react";
import { Table } from 'react-bootstrap';

const Realtime = () => {
  const [transactions, setTransactions] = useState([]);

  useEffect(() => {
    const eventSource = new EventSource("http://localhost:8080/realtime/sse");
    eventSource.onmessage = (event) => {
      const result = JSON.parse(event.data);
      setTransactions((prevEvents) => [result, ...prevEvents]);   
    };    
    return () => eventSource.close();
  }, []);

  return (
    <div>
        <h5>Realtime Transactions</h5>
        <Table striped bordered hover>
        <thead>
            <tr>
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
  );
};

export default Realtime;